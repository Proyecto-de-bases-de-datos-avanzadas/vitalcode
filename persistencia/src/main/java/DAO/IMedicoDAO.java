package DAO;

import Exception.PersistenciaException;
import entidades.Medico;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IMedicoDAO {
    public boolean agregarMedico(Medico medico) throws PersistenciaException;
    
    public boolean darDeBajaMedico(int idUsuario) throws PersistenciaException;
}
