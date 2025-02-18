package DAO;

import Exception.PersistenciaException;
import entidades.Paciente;

/**
 *
 * @author JR
 */
public interface IPacienteDAO {
    /**
     * Agrega un nuevo paciente a la base de datos
     */
    public boolean agregarPaciente(int idUsuario, String correoElectronicoPaciente, String nombrePaciente, String apellidoPaterno, String apellidoMaterno, String telefono) throws PersistenciaException;
    
    /**
     * Consulta un paciente por su ID de usuario
     */
    public Paciente consultarPacientePorID(int idUsuario) throws PersistenciaException;
}
