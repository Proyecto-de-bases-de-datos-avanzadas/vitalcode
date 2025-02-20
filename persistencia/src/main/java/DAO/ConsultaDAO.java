/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
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
}
