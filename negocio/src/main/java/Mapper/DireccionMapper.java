/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
