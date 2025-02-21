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
    
}