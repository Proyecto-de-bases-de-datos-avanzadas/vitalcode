package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Medico;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public boolean agregarMedico(Medico medico) throws PersistenciaException {
        String sql = "{CALL agregar_medico(?, ?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, medico.getIdUsuario());
            cs.setString(2, medico.getNombre());
            cs.setString(3, medico.getEspecialidadMedico());
            cs.setString(4, medico.getCedulaMedico());
            cs.setString(5, medico.getEstadoMedico().toString()); // Suponiendo que 'estado' es un ENUM de 'Activo' o 'Inactivo'

            int filasInsertadas = cs.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al agregar el médico en la base de datos.");
        }
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
}
