package DAO;

import Exception.PersistenciaException;
import entidades.Usuario;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IUsuarioDAO {
    
    public Usuario agregarUsuario(Usuario usuario) throws PersistenciaException;
    
    public Usuario consultarUsuarioPorID(int idUsuario) throws PersistenciaException;
    
    public Usuario buscarUsuarioPorUsuario(String usuario) throws PersistenciaException;
}


