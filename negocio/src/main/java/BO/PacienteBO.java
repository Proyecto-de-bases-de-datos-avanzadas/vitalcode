package BO;

import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNDTO;
import DTO.PacienteVDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.PacienteMapper;
import conexion.IConexionBD;
import entidades.Paciente;
import entidades.Usuario;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteBO {

    private final PacienteDAO pacienteDAO;

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }
   public boolean registrarPaciente(Usuario usuario, Paciente paciente) throws PersistenciaException, NegocioException{
        //revisar que usuario y paciente no sean nulos
        if(usuario== null || paciente ==null){
            throw new NegocioException("usuario y paciente no pueden ser nulos");
        }
        // campos obligatorios 
        if(usuario.getNombre_usuario().isEmpty() || usuario.getContraseniaUsuario().isEmpty()){
            throw new NegocioException("usuario y password son obligatorios");
        }
        if(paciente.getCorreoElectronicoPaciente().isEmpty()|| paciente.getNombrePaciente().isEmpty() 
                || paciente.getApellidoPaterno().isEmpty() || paciente.getApellidoMateno().isEmpty()){
            throw new NegocioException("Todos los datos son obligatorios");
        }
        
        PacienteMapper convertidor = new PacienteMapper();
       Paciente pacienteEntity = convertidor.toEntity(paciente); 

        try {
            return pacienteDAO.agregarUsuarioYPaciente(usuario, pacienteEntity);
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("Hubo un error al registrar el paciente en la base de datos", ex);
        }
   }
   
   
}