package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Direccion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ErnestoLpz_252663
 */
public class DireccionDAO {
    private IConexionBD conexion;
    
    public DireccionDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    // Método para agregar dirección
    public void agregarDireccion(Direccion direccion) throws PersistenciaException {
        String sentenciaSQL = "INSERT INTO direccion (id_Paciente, calle, numero, colonia) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
                PreparedStatement ps = conn.prepareCall(sentenciaSQL)) {
            ps.setInt(1, direccion.getId_Paciente());
            ps.setString(2, direccion.getCalle());
            ps.setString(3, direccion.getNumero());
            ps.setString(4, direccion.getColonia());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregar la direccion...");
        }
    }

    // Método para obtener dirección por ID de paciente
    public Direccion obtenerDireccionPorIdPaciente(int idPaciente) throws PersistenciaException {
        String sentenciaSQL = "SELECT * FROM direccion WHERE id_Paciente = ?";
        try (Connection conn = conexion.crearConexion();
                PreparedStatement ps = conn.prepareCall(sentenciaSQL)) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Direccion(
                        rs.getInt("id_Paciente"),
                        rs.getString("calle"),
                        rs.getString("numero"),
                        rs.getString("colonia")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener la direccion...");
        }
        return null;
    }

    // Método para actualizar dirección
    public boolean  actualizarDireccion(Direccion direccion) throws PersistenciaException {
        String sentenciaSQL = "UPDATE direccion SET calle = ?, numero = ?, colonia = ? WHERE id_Paciente = ?";
        try (Connection conn = conexion.crearConexion();
                PreparedStatement ps = conn.prepareCall(sentenciaSQL)) {
            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getNumero());
            ps.setString(3, direccion.getColonia());
            ps.setInt(4, direccion.getId_Paciente());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar la direccion...");
        }
    }
}
