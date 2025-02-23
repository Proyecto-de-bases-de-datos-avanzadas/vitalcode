/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.DireccionDAO;
import DTO.DireccionNDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.DireccionMapper;
import conexion.IConexionBD;
import entidades.Direccion;

/**
 *
 * @author erika
 */
public class DireccionBO {
    private final DireccionDAO direccionDAO;
    
    public DireccionBO(IConexionBD conexion){
       this.direccionDAO = new DireccionDAO(conexion);
    }
    
    public DireccionNDTO recuperarDireccionPorID (int id) throws PersistenciaException{
        Direccion direccionRecuperada = direccionDAO.obtenerDireccionPorIdPaciente(id);
        DireccionMapper convertidorDireccion = new DireccionMapper();
        DireccionNDTO direccionRec = convertidorDireccion.toDTO(direccionRecuperada);
        return direccionRec;
    }
    
    public boolean actualizarDireccion(DireccionNDTO direccionDTO) throws PersistenciaException, NegocioException {
        if (direccionDTO == null) {
            throw new NegocioException("La dirección no puede ser nula");
        }
        DireccionMapper convertidorDireccion = new DireccionMapper();
        Direccion direccion = convertidorDireccion.ToEntity(direccionDTO);
        return direccionDAO.actualizarDireccion(direccion);
    }
}
