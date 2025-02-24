package BO;

import DAO.MedicoDAO;
import DTO.CitaDTO;
import DTO.MedicoDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.MedicoMapper;
import conexion.IConexionBD;
import entidades.Cita;
import entidades.Horario;
import entidades.Medico;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public MedicoDTO recuperarMedicoUsuario (String usuario ) throws PersistenciaException{
        Medico medicoRecuperado = medicoDAO.consultarMedicoPorNombre(usuario);
        MedicoMapper convertidorMedico = new MedicoMapper();
        MedicoDTO medico = convertidorMedico.toDTO(medicoRecuperado);
        return medico;
    }
    
    public List<Horario> recuperarHorarioMedico(int idMedico) throws NegocioException, PersistenciaException {
        try {
            return medicoDAO.consultarHorarioMedico(idMedico);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al recuperar el horario del médico con ID: " + idMedico, e);
        }
    }
    
    public boolean BajaMedico (int idMedico){
        try {
            return medicoDAO.darDeBajaMedico(idMedico);
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean AltaMedico (int idMedico){
        try {
            return medicoDAO.activarMedico(idMedico);
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     // Método para obtener las citas de un medico cuya fecha aún no ha pasado y su estado es pendiente
    public List<CitaDTO> obtenerCitasPendientes(int idMedico)  {
        try {
            List<CitaDTO> citasDTO = new ArrayList<>();
            List<Cita> citas = medicoDAO.obtenerTodasLasCitasPendientes(idMedico);
            
            for (Cita cita : citas) {
                CitaDTO citaDTO = MedicoMapper.toDTO(cita);
                citasDTO.add(citaDTO);
            }
            
            return citasDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
         // Método para obtener las citas de un medico 
    public List<CitaDTO> obtenerCitas(int idMedico)  {
        try {
            List<CitaDTO> citasDTO = new ArrayList<>();
            List<Cita> citas = medicoDAO.obtenerTodasLasCitas(idMedico);
            
            for (Cita cita : citas) {
                CitaDTO citaDTO = MedicoMapper.toDTO(cita);
                citasDTO.add(citaDTO);
            }
            
            return citasDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
             // Método para obtener las citas de un medico ordenadas por fecha
    public List<CitaDTO> obtenerCitasFecha(int idMedico)  {
        try {
            List<CitaDTO> citasDTO = new ArrayList<>();
            List<Cita> citas = medicoDAO.obtenerTodasLasCitasPorFecha(idMedico);
            
            for (Cita cita : citas) {
                CitaDTO citaDTO = MedicoMapper.toDTO(cita);
                citasDTO.add(citaDTO);
            }
            
            return citasDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    // Método para obtener las citas de un medico ordenadas por estado
    public List<CitaDTO> obtenerCitasEstado(int idMedico)  {
        try {
            List<CitaDTO> citasDTO = new ArrayList<>();
            List<Cita> citas = medicoDAO.obtenerTodasLasCitasPorEstado(idMedico);
            
            for (Cita cita : citas) {
                CitaDTO citaDTO = MedicoMapper.toDTO(cita);
                citasDTO.add(citaDTO);
            }
            
            return citasDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public Map<String, List<Time>> obtenerIntervalosMedico(int idMedico) throws PersistenciaException {
        return medicoDAO.consultarIntervalosMedico(idMedico);
    }
    
    
}
