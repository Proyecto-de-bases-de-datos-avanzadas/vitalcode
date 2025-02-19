package DAO;

import Exception.PersistenciaException;
import entidades.Paciente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
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

    //Método para agregar un paciente nuevo usando un procedimiento almacenado
    public boolean agregarPaciente(Paciente paciente) throws PersistenciaException {
        String sentenciaSQL = "{CALL agregar_paciente(?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conexion.prepareCall(sentenciaSQL)) {
            cs.setInt(1, paciente.getIdUsuario());
            cs.setString(2, paciente.getCorreoElectronicoPaciente());
            cs.setString(3, paciente.getNombrePaciente());
            cs.setString(4, paciente.getApellidoPaterno());
            cs.setString(5, paciente.getApellidoMateno());
            cs.setString(6, paciente.getTelefono());
            cs.setDate(7, paciente.getFechaNacPaciente());

            int filasInsertadas = cs.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al agregar el paciente en la base de datos.");
        }
    }

    // Método para consultar un paciente por su ID
    public Paciente consultarPacientePorID(int idUsuario) throws PersistenciaException {
        String sentenciaSQL = "SELECT * FROM PACIENTE WHERE id_usuario = ?"; // Ajusté el nombre de la columna
        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL)) {
            ps.setInt(1, idUsuario);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Paciente paciente = new Paciente();
                    paciente.setIdUsuario(rs.getInt("id_usuario")); // Ajusté el nombre de la columna
                    paciente.setCorreoElectronicoPaciente(rs.getString("correoElectronico"));
                    paciente.setNombrePaciente(rs.getString("nombre"));
                    paciente.setApellidoPaterno(rs.getString("apellidoPat"));
                    paciente.setApellidoMateno(rs.getString("apellidoMat"));
                    paciente.setTelefono(rs.getString("telefono"));
                    
                    return paciente;
                } else {
                    throw new PersistenciaException("El paciente con el ID: " + idUsuario + " no ha sido encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al consultar el paciente en la base de datos.");
        }
    }
}