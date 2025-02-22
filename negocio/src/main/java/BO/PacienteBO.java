package BO;

import DAO.DireccionDAO;
import DAO.PacienteDAO;
import DTO.DireccionNDTO;
import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.PacienteMapper;
import Mapper.UsuarioMapper;
import conexion.IConexionBD;
import entidades.Direccion;
import entidades.Paciente;
import entidades.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteBO {

    private final PacienteDAO pacienteDAO;
    private final DireccionDAO direccionDAO;
    

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
        this.direccionDAO = new DireccionDAO(conexion);
    }

    public int registrarPaciente(UsuarioNDTO usuario, PacienteNDTO paciente, DireccionNDTO direccion) throws PersistenciaException, NegocioException {
        // Revisar que usuario, paciente y dirección no sean nulos
        if (usuario == null || paciente == null || direccion == null) {
            throw new NegocioException("Usuario, paciente y dirección no pueden ser nulos");
        }
        // Campos obligatorios
        if (usuario.getNombre_usuario().isEmpty() || usuario.getContraseniaUsuario().isEmpty()) {
            throw new NegocioException("Usuario y password son obligatorios");
        }
        if (paciente.getCorreoElectronico().isEmpty() || paciente.getNombre().isEmpty()
                || paciente.getApellidoPaterno().isEmpty() || paciente.getApellidoMaterno().isEmpty()) {
            throw new NegocioException("Todos los datos son obligatorios");
        }
        if(!paciente.getCorreoElectronico().contains("@")){
            throw new NegocioException("Formato de correo no valido.");
        }
        PacienteMapper convertidorPaciente = new PacienteMapper();
        Paciente pacienteEntity = convertidorPaciente.toEntity(paciente);

        UsuarioMapper convertidorUsuario = new UsuarioMapper();
        Usuario usuarioEntity = convertidorUsuario.toEntity(usuario);

        // Agregar usuario y paciente
        boolean usuarioYPacienteAgregado = pacienteDAO.agregarUsuarioYPaciente(usuarioEntity, pacienteEntity);
        if (!usuarioYPacienteAgregado) {
            throw new PersistenciaException("Error al registrar usuario y paciente.");
        }
        // Obtener el ID generado del paciente
        int idPacienteGenerado = pacienteEntity.getIdUsuario();
        // Agregar dirección
        Direccion direccionEntity = new Direccion();
        direccionEntity.setId_Paciente(pacienteEntity.getIdUsuario());
        direccionEntity.setCalle(direccion.getCalle());
        direccionEntity.setNumero(direccion.getNumero());
        direccionEntity.setColonia(direccion.getColonia());

        direccionDAO.agregarDireccion(direccionEntity);

        return idPacienteGenerado;
    }
    public PacienteNDTO recuperarPacienteID(int idPaciente) throws PersistenciaException {        
        Paciente pacienteRecuperado = pacienteDAO.consultarPacientePorID(idPaciente);
        PacienteMapper convertidorPaciente = new PacienteMapper();
        PacienteNDTO paciente = convertidorPaciente.toDTO(pacienteRecuperado);


        return paciente;
    }
    
    public boolean ActualizarPaciente(PacienteNDTO pacienteDTO ) throws NegocioException, PersistenciaException {
        if(pacienteDTO ==null){
            throw new NegocioException("Paciente no puede ser nulo");
        }
        
        PacienteMapper convertidorPaciente = new PacienteMapper();
        Paciente paciente = convertidorPaciente.toEntity(pacienteDTO);
        return pacienteDAO.ActualizarPaciente(paciente);
    }

}
