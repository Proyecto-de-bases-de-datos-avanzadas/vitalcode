package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Cita;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class CitaDAO {
    private IConexionBD conexion;
    
    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    
    //agrgar cita
    public Cita agendarCita(Cita cita) throws PersistenciaException {
        String sqlValidacion = "SELECT COUNT(*) FROM Cita WHERE id_medico = ? AND DATE(fechaHora) = ? AND TIME(fechaHora) BETWEEN ? AND ?";
        String sqlCita = "{CALL agregar_cita(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pst = conn.prepareStatement(sqlValidacion)) {
                pst.setInt(1, cita.getIdMedico());
                pst.setDate(2, Date.valueOf(cita.getFecha().toLocalDate()));
                pst.setTime(3, Time.valueOf("07:00:00"));
                pst.setTime(4, Time.valueOf("22:00:00"));

                ResultSet rs = pst.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    // Si ya existen citas en ese horario se lanza la exception
                    throw new PersistenciaException("El médico no está disponible en este horario.");
                }
            }

            // 2. Si está disponible, se añade la cita
            
            Timestamp timestamp = new Timestamp(cita.getFecha().getTime());

            try (CallableStatement csCita = conn.prepareCall(sqlCita)) {
                csCita.setInt(1, cita.getIdPaciente());
                csCita.setInt(2, cita.getIdMedico());
                csCita.setTimestamp(3, timestamp);
                csCita.setString(4, cita.getEstadoCita());
                csCita.setInt(5, cita.getFolioCita());
                csCita.setString(6, cita.getTipoCita());

                int filasInsertadas = csCita.executeUpdate();

                if (filasInsertadas <= 0) {
                    conn.rollback();
                    throw new PersistenciaException("No se pudo registrar la cita.");
                }
            }
            conn.commit();
            return cita;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al registrar la cita: " + e.getMessage(), e);
        }
    }
        // Consultar cita por ID
    public Cita consultarCitaPorID(int idCita) throws PersistenciaException {
    String sql = "SELECT * FROM Cita WHERE id = ?";
    try (Connection conn = conexion.crearConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idCita);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Cita(
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getDate("fechaHora"),
                    rs.getString("estado"),
                    rs.getString("tipoDeCita")
                );
            } else {
                throw new PersistenciaException("Cita no encontrada.");
            }
        }
    } catch (SQLException e) {
        throw new PersistenciaException("Error al consultar la cita: " + e.getMessage(), e);
    }
}
        // Cancelar cita (cambiar estado a 'Cancelada')
    public boolean cancelarCita(int idCita) throws PersistenciaException {
        String sql = "UPDATE Cita SET estado = 'Cancelada' WHERE id = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCita);

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al cancelar la cita: " + e.getMessage(), e);
        }
    }
    
    //???
    public boolean agendarCitaEmergencia(int idPaciente) throws PersistenciaException {
        String sql = "{CALL asignar_cita_emergencia(?)}";

        try (Connection conn = conexion.crearConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPaciente);
            boolean resultado = cs.execute();

            // Obtener el mensaje del procedimiento almacenado
            ResultSet rs = cs.getResultSet();
            if (rs.next()) {
                System.out.println(rs.getString("mensaje"));
            }

            return resultado;
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al asignar cita de emergencia.", ex);
        }
    }
}