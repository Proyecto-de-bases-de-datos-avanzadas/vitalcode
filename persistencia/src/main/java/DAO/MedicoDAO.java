package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Cita;
import entidades.Horario;
import entidades.Medico;
import entidades.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ErnestoLpz_252663
 */
public class MedicoDAO {
    private IConexionBD conexion;
    
    public MedicoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
// método para hacer los insert masivos
    public boolean agregarMedicosConHorarios(List<Medico> medicos, List<Usuario> usuarios) throws PersistenciaException {
        if (medicos.size() != usuarios.size()) {
            throw new IllegalArgumentException("El número de médicos y usuarios debe coincidir.");
        }

        String consultaSqlUsuario = "CALL AgregarUsuario(?, ?, ?, ?)"; // Procedimiento de uds
        String consultaSqlMedico = "INSERT INTO Medico (id_usuario, nombre, especialidad, cedulaProfesional, estado) VALUES (?, ?, ?, ?, 'Activo')"; // hubieran manejado default 'activo'
        String consultaSqlHorario = "INSERT INTO Horarios (diaSemana, horaEntrada, horaSalida) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE id=id"; // Evita duplicados
        /*
        explicacion del on duplicate key update
        Si ya existe (mismo diaSemana, horaEntrada y horaSalida), no lo duplica y simplemente deja el registro como está. 
        La parte id=id no cambia realmente nada en la base de datos, pero obliga a MySQL a devolver el ID existente en lugar de generar un error por clave duplicada. Esto nos permite obtener el ID del horario incluso si ya estaba en la base de datos, sin necesidad de hacer una consulta +
        
         */
        String consultaSqlMedicoHorario = "INSERT INTO Medico_Horario (id_medico, id_horario) VALUES (?, ?)";

        try (Connection conn = conexion.crearConexion()) {
            conn.setAutoCommit(false); // Deshabilitar autocommit para control manual (activa una transaccion por decirlo asi)

            try (CallableStatement csUsuario = conn.prepareCall(consultaSqlUsuario);
                    PreparedStatement psMedico = conn.prepareStatement(consultaSqlMedico, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement psHorario = conn.prepareStatement(consultaSqlHorario, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement psMedicoHorario = conn.prepareStatement(consultaSqlMedicoHorario)) {

                for (int i = 0; i < usuarios.size(); i++) {
                    Usuario usuario = usuarios.get(i);
                    Medico medico = medicos.get(i);

                    // Paso 1: Insertar usuario y obtener su id
                    String hashedPassword = BCrypt.hashpw(usuario.getContraseniaUsuario(), BCrypt.gensalt()); // metodo de uds

                    // valores de la entidad usuario
                    csUsuario.setString(1, usuario.getNombre_usuario());
                    csUsuario.setString(2, hashedPassword);
                    csUsuario.setString(3, "Medico");
                    csUsuario.registerOutParameter(4, Types.INTEGER);

                    csUsuario.execute();
                    int idUsuarioGenerado = csUsuario.getInt(4);

                    if (idUsuarioGenerado <= 0) {
                        conn.rollback();
                        throw new PersistenciaException("No se pudo obtener el ID generado del usuario.");
                    }

                    usuario.setIdUsuario(idUsuarioGenerado);
                    medico.setIdUsuario(idUsuarioGenerado);

                    // Paso 2: Insertar médico
                    psMedico.setInt(1, medico.getIdUsuario());
                    psMedico.setString(2, medico.getNombre());
                    psMedico.setString(3, medico.getEspecialidadMedico());
                    psMedico.setString(4, medico.getCedulaMedico());
                    psMedico.addBatch(); // el batch agrupa todas las inserciones en una sola comunicación con la BD, por lo que evitamos abrir muchas conexiones (Se usa addBatch() para agregar consultas y executeBatch() para ejecutarlas todas de una vez)
                }

                psMedico.executeBatch(); // Ejecutar inserciones de médicos

                // Paso 3: Insertar horarios y obtener sus IDs
                for (Medico medico : medicos) {
                    for (Horario horario : medico.getHorarioMedico()) {
                        psHorario.setString(1, horario.getDiaSemana());
                        psHorario.setTime(2, horario.getHoraEntrada());
                        psHorario.setTime(3, horario.getHoraSalida());
                        psHorario.addBatch();
                    }
                }
                psHorario.executeBatch(); // Ejecutar inserciones de horarios

                // Paso 4: Obtener IDs de horarios e insertar relaciones médico-horario
                try (ResultSet rsHorario = psHorario.getGeneratedKeys()) {
                    for (Medico medico : medicos) {
                        for (Horario horario : medico.getHorarioMedico()) {
                            if (rsHorario.next()) {
                                int idHorario = rsHorario.getInt(1);
                                psMedicoHorario.setInt(1, medico.getIdUsuario());
                                psMedicoHorario.setInt(2, idHorario);
                                psMedicoHorario.addBatch();
                            }
                        }
                    }
                }
                psMedicoHorario.executeBatch(); // Ejecutar inserciones de relaciones médico-horario

                conn.commit(); // Confirmar la transacción
                return true; // si todo salio bien

            } catch (SQLException ex) {
                conn.rollback();
                Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, "Error al agregar médicos", ex);
                throw new PersistenciaException("Error al agregar médicos en la base de datos.", ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, "Error en la conexión", ex);
        }

        return false;
    }
    // Método para agregar un médico
    public boolean agregarMedicoYUsuario(Medico medico, Usuario usuario) throws PersistenciaException {
        String sqlUsuario = "{CALL AgregarUsuario(?, ?, ?, ?)}";
        String sqlMedico = "INSERT INTO Medico (id_usuario, nombre, especialidad, cedulaProfesional, estado) VALUES (?, ?, ?, ?, ?)";
        
      try (Connection conn = conexion.crearConexion()) {
            conn.setAutoCommit(false);
            // Encriptar la contraseña :)
            String hashedPassword = BCrypt.hashpw(usuario.getContraseniaUsuario(), BCrypt.gensalt());
            // 1. Agregar el usuario y obtener el ID generado
            try (CallableStatement csUsuario = conn.prepareCall(sqlUsuario)) {
                csUsuario.setString(1, usuario.getNombre_usuario());
                csUsuario.setString(2, hashedPassword); // mandamos la contraseña ya encriptada wow 
                csUsuario.setString(3, usuario.getTipo_usuario());
                csUsuario.registerOutParameter(4, Types.INTEGER);
                
                csUsuario.execute();
                int idGenerado = csUsuario.getInt(4);
                
                if (idGenerado <= 0) {
                    conn.rollback();
                    throw new PersistenciaException("No se pudo obtener el ID generado del usuario.");
                }
                // asignar el id autogenerado
                 usuario.setIdUsuario(idGenerado);
                 medico.setIdUsuario(idGenerado);
                 
                 //agregar medico 
                 try (CallableStatement psMedico = conn.prepareCall(sqlMedico)) {
                psMedico.setInt(1, medico.getIdUsuario());
                psMedico.setString(2, medico.getNombre());
                psMedico.setString(3, medico.getEspecialidadMedico());
                psMedico.setString(4, medico.getCedulaMedico());
                psMedico.setString(5, medico.getEstadoMedico());

                int filasInsertadas = psMedico.executeUpdate();

                if (filasInsertadas <= 0) {
                    conn.rollback();
                    throw new PersistenciaException("No se pudo registrar el Medico.");
                }
            }
            conn.commit(); // Confirmar la transacción
            return true;   
                
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, "Error al agregar el médico", ex);
            throw new PersistenciaException("Error al agregar el médico en la base de datos.", ex);
        }
        }   catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    
    public boolean darDeBajaMedico(int idUsuario) throws PersistenciaException {
        String sql = "UPDATE Medico SET estado = 'Inactivo' WHERE id_usuario = ?";

        try (Connection conn = conexion.crearConexion();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al dar de baja al médico en la base de datos.");
        }
    }
    public boolean activarMedico (int idUsuario) throws PersistenciaException{
        String sql = "UPDATE Medico SET estado = 'Activo' WHERE id_usuario = ?";

        try (Connection conn = conexion.crearConexion();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al dar de baja al médico en la base de datos.");
        }
    }
    public Medico consultarMedicoID(int idMedico) throws PersistenciaException {
        String sql = "SELECT * FROM medico WHERE id_usuario = ?;";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico();
                     medico.setIdUsuario(rs.getInt("id_usuario"));
                    medico.setNombre(rs.getString("nombre"));
                    medico.setEspecialidadMedico(rs.getString("especialidad"));
                    medico.setCedulaMedico(rs.getString("cedulaProfesional"));
                    medico.setEstadoMedico(rs.getString("estado"));
                
                    return medico;
                } else {
                    throw new PersistenciaException("El médico con ID " + idMedico + " no ha sido encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, "Error al recuperar el médico con ID: " + idMedico, ex);
            throw new PersistenciaException("Error al recuperar el médico en la base de datos.", ex);
        }
    }
    
    public Medico consultarMedicoPorNombre(String nombreUsuario) throws PersistenciaException{
    String sql = "SELECT m.id_usuario, m.nombre, m.especialidad, m.cedulaProfesional, m.estado " +
                     "FROM Medico m " +
                     "INNER JOIN Usuario u ON m.id_usuario = u.id " +
                     "WHERE u.nombreUsuario = ?;";
    try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nombreUsuario);
        try(ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                Medico medico = new Medico();
                medico.setIdUsuario(rs.getInt("id_usuario"));
                medico.setNombre(rs.getString("nombre"));
                medico.setEspecialidadMedico(rs.getString("especialidad"));
                medico.setCedulaMedico(rs.getString("cedulaProfesional"));
                medico.setEstadoMedico(rs.getString("estado"));
                return medico;
            }    
             else{
                 throw new PersistenciaException("No se encontro medico");}
            
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Horario> consultarHorarioMedico(int idMedico) throws PersistenciaException {
        String sql = "SELECT h.id, h.diaSemana, h.horaEntrada, h.horaSalida " +
                     "FROM Horarios h " +
                     "INNER JOIN Medico_Horario mh ON h.id = mh.id_horario " +
                     "WHERE mh.id_medico = ?";

        List<Horario> horarios = new ArrayList<>();

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Horario horario = new Horario();
                    horario.setId(rs.getInt("id"));
                    horario.setDiaSemana(rs.getString("diaSemana"));
                    horario.setHoraEntrada(rs.getTime("horaEntrada"));
                    horario.setHoraSalida(rs.getTime("horaSalida"));
                    horarios.add(horario);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, "Error al recuperar el horario del médico con ID: " + idMedico, ex);
            throw new PersistenciaException("Error al recuperar el horario del médico en la base de datos.", ex);
        }

        return horarios;
    }
    public Map<String, List<Time>> consultarIntervalosMedico(int idMedico) throws PersistenciaException {
        List<Horario> horarios = consultarHorarioMedico(idMedico);
        return HorarioUtil.generarIntervalosPorDia(horarios);
    }
    
    public boolean actualizarEstadoMedico(int idMedico, String nuevoEstado) throws PersistenciaException {
        String sql = "UPDATE Medicos SET estado = ? WHERE idMedico = ?";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idMedico);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar el estado del médico", e);
        }
    }
    
    public List<Cita> obtenerTodasLasCitas (int idMedico) throws PersistenciaException{
        // declarar una lista para guardar todas las citas del medico
        List<Cita> citas = new ArrayList<>();
        
        String query = "SELECT * FROM Cita WHERE id_medico = ?";
        
        try (Connection conn = conexion.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMedico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setIdMedico(rs.getInt("id_medico"));
                    cita.setFecha(rs.getObject("fechaHora", LocalDateTime.class));
                    cita.setEstadoCita(rs.getString("estado"));
                    cita.setTipoCita(rs.getString("tipoDeCita"));
                    citas.add(cita);
                }
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    }
     public List<Cita> obtenerTodasLasCitasPendientes (int idMedico) throws PersistenciaException{
        // declarar una lista para guardar todas las citas del medico
        List<Cita> citas = new ArrayList<>();
        
        String query = "SELECT * FROM Cita WHERE id_medico = ? AND estado = 'Pendiente'";
        
        try (Connection conn = conexion.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMedico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setIdMedico(rs.getInt("id_medico"));
                    cita.setFecha(rs.getObject("fechaHora", LocalDateTime.class));
                    cita.setEstadoCita(rs.getString("estado"));
                    cita.setTipoCita(rs.getString("tipoDeCita"));
                    citas.add(cita);
                }
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    }   
         public List<Cita> obtenerTodasLasCitasPorEstado(int idMedico) throws PersistenciaException{
        // declarar una lista para guardar todas las citas del medico
        List<Cita> citas = new ArrayList<>();
        
        String query = "SELECT * FROM Cita WHERE id_medico = ? order by estado desc;";
        
        try (Connection conn = conexion.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMedico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setIdMedico(rs.getInt("id_medico"));
                    cita.setFecha(rs.getObject("fechaHora", LocalDateTime.class));
                    cita.setEstadoCita(rs.getString("estado"));
                    cita.setTipoCita(rs.getString("tipoDeCita"));
                    citas.add(cita);
                }
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    } 
            public List<Cita> obtenerTodasLasCitasPorFecha(int idMedico) throws PersistenciaException{
        // declarar una lista para guardar todas las citas del medico
        List<Cita> citas = new ArrayList<>();
        
        String query = "SELECT * FROM Cita WHERE id_medico = ? order by fechaHora;";
        
        try (Connection conn = conexion.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMedico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setIdMedico(rs.getInt("id_medico"));
                    cita.setFecha(rs.getObject("fechaHora", LocalDateTime.class));
                    cita.setEstadoCita(rs.getString("estado"));
                    cita.setTipoCita(rs.getString("tipoDeCita"));
                    citas.add(cita);
                }
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    } 
}