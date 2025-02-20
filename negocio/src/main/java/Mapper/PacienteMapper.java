package Mapper;

import DTO.PacienteNDTO;
import entidades.Paciente;


/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteMapper {
    public Paciente toEntity (PacienteNDTO pacienteNuevo){
        Paciente paciente = new Paciente(pacienteNuevo.getCorreoElectronico(), pacienteNuevo.getNombre(), pacienteNuevo.getApellidoPaterno(), pacienteNuevo.getApellidoMaterno(), pacienteNuevo.getTelefono(), pacienteNuevo.getFechaNacimiento());
        return paciente;
    }
    
    public PacienteNDTO toDTO(Paciente paciente){
        PacienteNDTO pacienteDTO = new PacienteNDTO(paciente.getCorreoElectronicoPaciente(), paciente.getNombrePaciente(), paciente.getApellidoPaterno(), paciente.getApellidoMateno(), paciente.getTelefono(), paciente.getFechaNacPaciente());
        return pacienteDTO;
    }
    
}
