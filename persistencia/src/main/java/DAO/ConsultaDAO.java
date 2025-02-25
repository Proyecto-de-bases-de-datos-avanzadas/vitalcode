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
        String sql = "SELECT * FROM Consulta WHERE id_medico = ?"; // SQL puede variar según tu estructura

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMedico);  // Asumiendo que 'id_medico' es un campo en tu base de datos
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta(
                        rs.getInt("idConsulta"),  // Asegúrate de que 'idConsulta' es el nombre de la columna en la DB
                        rs.getInt("idCita"),      // id de la cita
                        rs.getString("tipoConsulta"),  // tipo de consulta
                        rs.getDate("fecha"),      // fecha de la consulta (asumido que es un tipo Date en DB)
                        rs.getString("diagnosticoConsulta"),  // diagnóstico
                        rs.getString("notasConsulta")  // notas
                    );
                    consultas.add(consulta);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener las consultas del médico", e);
        }

        return consultas;
    }
}