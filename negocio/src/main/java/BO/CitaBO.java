/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.CitaDAO;
import conexion.IConexionBD;
import Exception.PersistenciaException;
import Exception.NegocioException;

/**
 *
 * @author alexnieblas
 */
public class CitaBO {
    private final CitaDAO citaDAO;
    
    public CitaBO(IConexionBD conexion){
        this.citaDAO= new CitaDAO(conexion);
    }
    
    public boolean citaDeEmergencia(int idPaciente) throws NegocioException{
        try{
            return citaDAO.agendarCitaEmergencia(idPaciente);
        }catch(PersistenciaException pe){
            throw new NegocioException("Error al agendar cita de emergencia para el paciente " + idPaciente);
        }
    }
}
