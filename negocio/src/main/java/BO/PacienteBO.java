package BO;

import DAO.PacienteDAO;
import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.PacienteMapper;
import Mapper.UsuarioMapper;
import conexion.IConexionBD;
import entidades.Paciente;
import entidades.Usuario;

/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteBO {

    private final PacienteDAO pacienteDAO;

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }
   public boolean registrarPaciente(UsuarioNDTO usuario, PacienteNDTO paciente) throws PersistenciaException, NegocioException{
        //revisar que usuario y paciente no sean nulos
        if(usuario== null || paciente ==null){
            throw new NegocioException("usuario y paciente no pueden ser nulos");
        }
        // campos obligatorios 
        if(usuario.getNombre_usuario().isEmpty() || usuario.getContraseniaUsuario().isEmpty()){
            throw new NegocioException("usuario y password son obligatorios");
        }
        if(paciente.getCorreoElectronico().isEmpty()|| paciente.getNombre().isEmpty() 
                || paciente.getApellidoPaterno().isEmpty() || paciente.getApellidoMaterno().isEmpty()){
            throw new NegocioException("Todos los datos son obligatorios");
        }
        
        PacienteMapper convertidorPaciente = new PacienteMapper();
        Paciente pacienteEntity = convertidorPaciente.toEntity(paciente);
        
        UsuarioMapper convertidorUsuario = new UsuarioMapper();
        Usuario usuarioEntity = convertidorUsuario.toEntity(usuario);
        
        return pacienteDAO.agregarUsuarioYPaciente(usuarioEntity, pacienteEntity);
   }
   
   
}