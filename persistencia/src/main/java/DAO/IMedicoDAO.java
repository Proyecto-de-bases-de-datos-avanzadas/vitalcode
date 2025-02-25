package DAO;

import Exception.PersistenciaException;
import entidades.Horario;
import entidades.Medico;
import entidades.Usuario;
import java.util.List;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IMedicoDAO {    
    public boolean agregarMedicoYUsuario(Medico medico, Usuario usuario) throws PersistenciaException;
    
    public boolean darDeBajaMedico(int idUsuario) throws PersistenciaException;
    
    public boolean activarMedico (int idUsuario) throws PersistenciaException;
    
    public Medico consultarMedicoID(int idMedico) throws PersistenciaException;
    
    public Medico consultarMedicoPorNombre(String nombreUsuario) throws PersistenciaException;
    
    public List<Horario> consultarHorarioMedico(int idMedico) throws PersistenciaException;
    
    public boolean actualizarEstadoMedico(int idMedico, String nuevoEstado) throws PersistenciaException;
}