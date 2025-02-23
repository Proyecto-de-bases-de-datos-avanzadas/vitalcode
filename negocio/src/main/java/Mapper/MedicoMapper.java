/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.MedicoDTO;
import entidades.Medico;

/**
 *
 * @author erika
 */
public class MedicoMapper {
    // Convertir de entidad a DTO
    public MedicoDTO toDTO(Medico medico) {
        return new MedicoDTO(
            medico.getIdUsuario(),
            medico.getNombre(),
            medico.getEspecialidadMedico(),
            medico.getCedulaMedico(),
            medico.getEstadoMedico()
        );
    }

    // Convertir de DTO a entidad
    public Medico toEntity(MedicoDTO medicoDTO) {
        Medico medico = new Medico();
        medico.setIdUsuario(medicoDTO.getId());
        medico.setNombre(medicoDTO.getNombre());
        medico.setEspecialidadMedico(medicoDTO.getEspecialidad());
        medico.setCedulaMedico(medicoDTO.getCedula());
        medico.setEstadoMedico(medicoDTO.getEstado());
        return medico;
    }
}   
