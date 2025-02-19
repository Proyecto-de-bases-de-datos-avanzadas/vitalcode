package DAO;

import Exception.PersistenciaException;
import entidades.Usuario;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IUsuarioDAO {
    
    Usuario agregarUsuario(Usuario usuario) throws PersistenciaException;
    
    Usuario consultarUsuarioPorID(int idUsuario) throws PersistenciaException;
    
    void actualizarUsuario(Usuario usuario) throws PersistenciaException;
    
    void eliminarUsuario(int idUsuario) throws PersistenciaException;
}


