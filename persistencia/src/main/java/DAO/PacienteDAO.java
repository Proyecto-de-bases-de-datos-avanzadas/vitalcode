package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Cita;
import entidades.Paciente;
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
public class PacienteDAO {
    private final IConexionBD conexion;

    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public boolean agregarUsuarioYPaciente(Usuario usuario, Paciente paciente) throws PersistenciaException {
        String sqlUsuario = "{CALL AgregarUsuario(?, ?, ?, ?)}";
        String sqlPaciente = "{CALL agregar_paciente(?, ?, ?, ?, ?, ?, ?)}";

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

                usuario.setIdUsuario(idGenerado);
                paciente.setIdUsuario(idGenerado);
            }

            // 2. Agregar el paciente con el ID obtenido
            try (CallableStatement csPaciente = conn.prepareCall(sqlPaciente)) {
                csPaciente.setInt(1, paciente.getIdUsuario());
                csPaciente.setString(2, paciente.getCorreoElectronicoPaciente());
                csPaciente.setString(3, paciente.getNombrePaciente());
                csPaciente.setString(4, paciente.getApellidoPaterno());
                csPaciente.setString(5, paciente.getApellidoMateno());
                csPaciente.setString(6, paciente.getTelefono());
                csPaciente.setDate(7, paciente.getFechaNacPaciente());

                int filasInsertadas = csPaciente.executeUpdate();

                if (filasInsertadas <= 0) {
                    conn.rollback();
                    throw new PersistenciaException("No se pudo registrar el paciente.");
                }
            }

            conn.commit(); // Confirmar la transacción
            return true;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al registrar usuario y paciente: " + e.getMessage(), e);
        }
    }

    // Método para consultar un paciente por su ID
    public Paciente consultarPacientePorID(int idUsuario) throws PersistenciaException {
        String sentenciaSQL = "SELECT id_usuario, correoElectronico, nombre, apellidoPat, apellidoMat, telefono, fechaNacimiento FROM PACIENTE WHERE id_usuario = ?";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sentenciaSQL)) {

            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setIdUsuario(rs.getInt("id_usuario"));
                    paciente.setCorreoElectronicoPaciente(rs.getString("correoElectronico"));
                    paciente.setNombrePaciente(rs.getString("nombre"));
                    paciente.setApellidoPaterno(rs.getString("apellidoPat"));
                    paciente.setApellidoMateno(rs.getString("apellidoMat"));
                    paciente.setTelefono(rs.getString("telefono"));
                    paciente.setFechaNacPaciente(rs.getDate("fechaNacimiento"));
                    
                    return paciente;
                } else {
                    throw new PersistenciaException("El paciente con ID " + idUsuario + " no ha sido encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al consultar el paciente en la base de datos.", ex);
        }
    }
    
    public boolean ActualizarPaciente(Paciente paciente) throws PersistenciaException{
        String sql = "UPDATE paciente SET correoElectronico = ?, nombre =? ,apellidoPat=? ,apellidoMat=? ,telefono=? ,fechaNacimiento =?  WHERE id_usuario = ?;";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paciente.getCorreoElectronicoPaciente());
            ps.setString(2, paciente.getNombrePaciente());
            ps.setString(3, paciente.getApellidoPaterno());
            ps.setString(4, paciente.getApellidoMateno());
            ps.setString(5, paciente.getTelefono());
            ps.setDate(6, new java.sql.Date(paciente.getFechaNacPaciente().getTime()));
            ps.setInt(7, paciente.getIdUsuario());
            
            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, "Error al actualizar el paciente con ID: " + paciente.getIdUsuario(), ex);
            throw new PersistenciaException("Error al actualizar el paciente en la base de datos.", ex);
        }
    }
    

    public List<Cita> obtenerTodasLasCitas(int idPaciente) throws SQLException {

        List<Cita> citas = new ArrayList<>();
        String query = "SELECT * FROM Cita WHERE id_paciente = ?";

        try (Connection conn = conexion.crearConexion();

         PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idPaciente);
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
        }
        return citas;
    }
    
    public List<Cita> obtenerCitasPendientes(int idPaciente) throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String query = "SELECT * FROM Cita WHERE id_paciente = ? AND  estado = 'Pendiente'";

      try (Connection conn = conexion.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idPaciente);
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
        }
        return citas;
    }

    
    public List<Cita> obtenerCitasOrdenadasPorFecha(int idPaciente) throws SQLException {
    List<Cita> citas = new ArrayList<>();
    String query = "SELECT * FROM Cita WHERE id_paciente = ? ORDER BY fechaHora";

    try (Connection conn = conexion.crearConexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, idPaciente);
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
    }
    return citas;}
    
    public List<Cita> obtenerCitasOrdenadasPorEspecialidad(int idPaciente) throws SQLException {
    List<Cita> citas = new ArrayList<>();
    String query = "SELECT c.* FROM Cita c JOIN Medico m ON c.id_medico = m.id_usuario WHERE c.id_paciente = ? ORDER BY m.especialidad";

    try (Connection conn = conexion.crearConexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, idPaciente);
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
    }
    return citas;
}

}