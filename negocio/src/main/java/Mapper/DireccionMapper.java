package Mapper;

import DTO.DireccionNDTO;
import entidades.Direccion;

/**
 *
 * @author erika
 */
public class DireccionMapper {
    public Direccion ToEntity (DireccionNDTO direccionDTO){
        return new Direccion(direccionDTO.getIdPaciente(), direccionDTO.getCalle(),direccionDTO.getNumero(),direccionDTO.getColonia());
    }
    
    public DireccionNDTO toDTO (Direccion direccion){
        return new DireccionNDTO(direccion.getId_Paciente(), direccion.getCalle(), direccion.getNumero(), direccion.getColonia());
    }
}
