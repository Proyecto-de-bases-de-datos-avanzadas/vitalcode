package BO;


import DAO.UsuarioDAO;
import DTO.UsuarioNDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.UsuarioMapper;
import conexion.IConexionBD;
import entidades.Usuario;

/**
 *
 * @author ErnestoLpz_252663
 */
public class UsuarioBO {
  private final UsuarioDAO usuarioDAO;

    public UsuarioBO(IConexionBD conexion){
        this.usuarioDAO = new UsuarioDAO(conexion);
    }
    public UsuarioNDTO recuperarUsuarioPorNombre(String nombre) throws NegocioException, PersistenciaException{
        Usuario usuarioRecuperado = usuarioDAO.buscarUsuarioPorUsuario(nombre);
        UsuarioMapper convertidorUsuario = new UsuarioMapper();
        UsuarioNDTO usuario = convertidorUsuario.toDTO(usuarioRecuperado);
        return usuario;
    }
}
