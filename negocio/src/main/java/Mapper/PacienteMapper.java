package Mapper;

import DTO.PacienteNDTO;
import entidades.Paciente;


/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteMapper {
   public Paciente toEntity(PacienteNDTO pacienteDTO) {
        if (pacienteDTO == null) {
            return null;
        }
        
        Paciente paciente = new Paciente();
        paciente.setIdUsuario(pacienteDTO.getIdUsuario());
        paciente.setCorreoElectronicoPaciente(pacienteDTO.getCorreoElectronico());
        paciente.setNombrePaciente(pacienteDTO.getNombre());
        paciente.setApellidoPaterno(pacienteDTO.getApellidoPaterno());
        paciente.setApellidoMateno(pacienteDTO.getApellidoMaterno());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setFechaNacPaciente(pacienteDTO.getFechaNacimiento());
        
        return paciente;
    }

    public PacienteNDTO toDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        
        PacienteNDTO pacienteDTO = new PacienteNDTO();
        pacienteDTO.setIdUsuario(paciente.getIdUsuario());
        pacienteDTO.setCorreoElectronico(paciente.getCorreoElectronicoPaciente());
        pacienteDTO.setNombre(paciente.getNombrePaciente());
        pacienteDTO.setApellidoPaterno(paciente.getApellidoPaterno());
        pacienteDTO.setApellidoMaterno(paciente.getApellidoMateno());
        pacienteDTO.setTelefono(paciente.getTelefono());
        pacienteDTO.setFechaNacimiento(paciente.getFechaNacPaciente());
        
        return pacienteDTO;
    }
    
}
