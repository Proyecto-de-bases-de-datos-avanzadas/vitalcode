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
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
                    medico.setEspecialidadMedico("especialidad");
                    medico.setCedulaMedico("cedula");
                    medico.setEstadoMedico("estado");
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
        
        
    
}   