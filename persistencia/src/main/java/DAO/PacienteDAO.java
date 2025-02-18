package DAO;

import Exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteDAO {
    private Connection conexion;
    
    public PacienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    //Método para agregar un paciente nuevo
    public boolean agregarPaciente(int idUsuario, String correoElectronicoPaciente, String nombrePaciente, String apellidoPaterno, String apellidoMaterno, String telefono) throws PersistenciaException{
        String sentenciaSQL = "INSERT INTO PACIENTE (idUsuario, correoElectronicoPaciente, nombrePaciente, apellidoPaterno, apellidoMaterno, telefono) VALUES (?, ?, ?, ?, ?, ?)" ;
        try(PreparedStatement ps = conexion.prepareStatement(sentenciaSQL)){
            ps.setInt(1, idUsuario);
            ps.setString(2, correoElectronicoPaciente);
            ps.setString(3, nombrePaciente);
            ps.setString(4, apellidoPaterno);
            ps.setString(5, apellidoMaterno);
            ps.setString(6, telefono);
            
            int filasAf = ps.executeUpdate();
            if (filasAf == 0) {
                throw new PersistenciaException("No se pudo añadir el paciente. Inténtelo de nuevo...");
            }else{
                System.out.println("Paciente añadido con éxito :)");
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, "Error al agregar paciente", ex);
            throw new PersistenciaException("Error al agregar paciente: " + ex.getMessage());
        }
    }
}