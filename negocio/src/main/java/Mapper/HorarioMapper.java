package Mapper;

import DTO.HorarioDTO;
import entidades.Horario;

/**
 *
 * @author erika
 */
public class HorarioMapper {
    public static HorarioDTO toDTO(Horario horario) {
        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario.getId());
        dto.setDiaSemana(horario.getDiaSemana());
        dto.setHoraEntrada(horario.getHoraEntrada());
        dto.setHoraSalida(horario.getHoraSalida());
        return dto;
    }
}
