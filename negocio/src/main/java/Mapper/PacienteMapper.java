package Mapper;

import DTO.CitaDTO;
import DTO.PacienteNDTO;
import entidades.Cita;
import entidades.Paciente;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteMapper {
    
    public static CitaDTO toDTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setIdCita(cita.getIdCita());
        dto.setIdPaciente(cita.getIdPaciente());
        dto.setIdMedico(cita.getIdMedico());
        dto.setFecha(cita.getFecha());
        dto.setEstadoCita(cita.getEstadoCita());
        dto.setTipoCita(cita.getTipoCita());
        return dto;
    }
    
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