package Mapper;

import DTO.CitaDTO;
import entidades.Cita;

/**
 *
 * @author erika
 */
public class CitaMapper {
    public static CitaDTO toDto(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setIdPaciente(cita.getIdPaciente());
        dto.setIdMedico(cita.getIdMedico());
        dto.setFecha(cita.getFecha());
        dto.setEstadoCita(cita.getEstadoCita());
        dto.setTipoCita(cita.getTipoCita());
        return dto;
    }

    public static Cita toEntity(CitaDTO dto) {
        Cita cita = new Cita();
        cita.setIdPaciente(dto.getIdPaciente());
        cita.setIdMedico(dto.getIdMedico());
        cita.setFecha(dto.getFecha());
        cita.setEstadoCita(dto.getEstadoCita());
        cita.setTipoCita(dto.getTipoCita());
        return cita;
    }
}
