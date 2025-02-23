package BO;

import DAO.MedicoDAO;
import DTO.MedicoDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.MedicoMapper;
import conexion.IConexionBD;
import entidades.Horario;
import entidades.Medico;
import java.util.List;
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
}
