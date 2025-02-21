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
        String sqlCita = "{CALL agregar_cita(?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion()) {
            conn.setAutoCommit(false);

            // 1. Agregar la cita
            try (CallableStatement csCita = conn.prepareCall(sqlCita)) {
                csCita.setInt(1, cita.getIdPaciente());
                csCita.setInt(2, cita.getIdMedico());
                csCita.setDate(3, cita.getFecha());
                csCita.setString(4, cita.getEstadoCita());

                int filasInsertadas = csCita.executeUpdate();

                if (filasInsertadas <= 0) {
                    conn.rollback();
                    throw new PersistenciaException("No se pudo registrar la cita.");
                }
            }

            conn.commit(); // Confirmar la transacción
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
        String sql = "SELECT idCita, idMedico, fechaHora " +
                     "FROM Cita " +
                     "WHERE estado = 'Disponible' " +
                     "ORDER BY fechaHora ASC " +
                     "LIMIT 1";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int idCita = rs.getInt("idCita");
                int idMedico = rs.getInt("idMedico");
                Timestamp fechaHora = rs.getTimestamp("fechaHora");

                // Actualizar la cita asignándola al paciente
                String updateSql = "UPDATE Cita SET idPaciente = ?, estado = 'Asignada' WHERE idCita = ?";
                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setInt(1, idPaciente);
                    updatePs.setInt(2, idCita);
                    int filasActualizadas = updatePs.executeUpdate();

                    if (filasActualizadas > 0) {
                        System.out.println("Cita de emergencia asignada al paciente con éxito.");
                        System.out.println("Detalles: Médico ID " + idMedico + ", Fecha: " + fechaHora);
                        return true;
                    }
                }
            } else {
                System.out.println("No hay citas disponibles en este momento.");
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al asignar cita de emergencia.", ex);
        }
        return false;
    }
}