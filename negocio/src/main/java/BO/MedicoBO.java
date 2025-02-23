/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.MedicoDAO;
import DTO.MedicoDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.MedicoMapper;
import conexion.IConexionBD;
import entidades.Medico;

/**
 *
 * @author erika
 */
public class MedicoBO {
    private final MedicoDAO medicoDAO;
    
    public MedicoBO(IConexionBD conexion){
        this.medicoDAO= new MedicoDAO(conexion);
    }
    
    public MedicoDTO recuperarMedicoID (int id) throws PersistenciaException{
        Medico medicoRecuperado = medicoDAO.consultarMedicoID(id);
        MedicoMapper convertidorMedico = new MedicoMapper();
        MedicoDTO medico = convertidorMedico.toDTO(medicoRecuperado);
        return medico; 
        
    }
}
