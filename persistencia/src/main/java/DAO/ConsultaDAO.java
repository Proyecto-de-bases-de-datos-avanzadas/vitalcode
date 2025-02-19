/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Exception.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JR
 */
public class ConsultaDAO {

    private final Connection conexion;

    public ConsultaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Procedimiento para agregar una Consulta
    public boolean agregarConsulta(int idCita, String tipoDeConsulta, Date fechaHora, String diagnostico, String notas) throws PersistenciaException {
        String sql = "{CALL agregar_consulta(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idCita);
            cs.setString(2, tipoDeConsulta);
            cs.setDate(3, fechaHora);
            cs.setString(4, diagnostico);
            cs.setString(5, notas);

            return cs.executeUpdate() > 0; // Devuelve true si se insertó la consulta
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, "Error al agregar consulta: " + ex.getMessage(), ex);
            throw new PersistenciaException("Error al agregar la consulta...");
        }
    }
    
    public boolean eliminarConsulta(int idConsulta) throws PersistenciaException {
        String sql = "DELETE FROM Consulta WHERE id = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);

            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;  // Retorna true si se eliminó al menos una fila
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al eliminar la consulta en la base de datos.");
        }
    }
}
