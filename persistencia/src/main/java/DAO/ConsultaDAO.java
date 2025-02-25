package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Consulta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class ConsultaDAO {

    private IConexionBD conexion;
    
    public ConsultaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public boolean agregarConsulta(int idCita, String tipoDeConsulta, Date fechaHora, String diagnostico, String notas) throws PersistenciaException {
        String sentenciaSQL = "{CALL agregar_consulta(?, ?, ?, ?, ?)}";
        try (Connection conn = conexion.crearConexion();
                CallableStatement cs = conn.prepareCall(sentenciaSQL)) {
            cs.setInt(1, idCita);
            cs.setString(2, tipoDeConsulta);
            cs.setDate(3, fechaHora);
            cs.setString(4, diagnostico);
            cs.setString(5, notas);

            return cs.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, "Error al agregar consulta: " + ex.getMessage(), ex);
            throw new PersistenciaException("Error al agregar la consulta...");
        }
    }
    
    public boolean eliminarConsulta(int idConsulta) throws PersistenciaException {
        String sentenciaSQL = "DELETE FROM Consulta WHERE id = ?";
        
        try (Connection conn = conexion.crearConexion();
                PreparedStatement ps = conn.prepareCall(sentenciaSQL)) {
            ps.setInt(1, idConsulta);

            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al eliminar la consulta en la base de datos.");
        }
    }
    
    public List<Consulta> obtenerConsultasPorMedico(int idMedico) throws PersistenciaException {
        List<Consulta> consultas = new ArrayList<>();
        String sentenciaSQL = "SELECT id, idCita, tipoDeConsulta, fechaHora, diagnostico, notas FROM Consulta WHERE idMedico = ?";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sentenciaSQL)) {
            ps.setInt(1, idMedico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idCita = rs.getInt("idCita");
                    String tipoDeConsulta = rs.getString("tipoDeConsulta");

                    Timestamp timestamp = rs.getTimestamp("fechaHora");
                    LocalDateTime fechaHora = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                    String diagnostico = rs.getString("diagnostico");
                    String notas = rs.getString("notas");

                    consultas.add(new Consulta(id, idCita, tipoDeConsulta, fechaHora, diagnostico, notas));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, "Error al obtener consultas: " + ex.getMessage(), ex);
            throw new PersistenciaException("Error al obtener las consultas...");
        }
        return consultas;
    }
}
