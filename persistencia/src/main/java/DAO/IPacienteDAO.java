package DAO;

import Exception.PersistenciaException;
import entidades.Paciente;
import entidades.Usuario;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IPacienteDAO {
    public boolean agregarPaciente(int idUsuario, String correoElectronicoPaciente, String nombrePaciente, String apellidoPaterno, String apellidoMaterno, String telefono) throws PersistenciaException;

    public Paciente consultarPacientePorID(int idUsuario) throws PersistenciaException;
    
    public boolean ActualizarPaciente(Paciente paciente) throws PersistenciaException;
    
    public boolean agregarUsuarioYPaciente(Usuario usuario, Paciente paciente) throws PersistenciaException;
}
