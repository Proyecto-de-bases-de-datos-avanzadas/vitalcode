package Mapper;

import DTO.UsuarioNDTO;
import entidades.Usuario;

/**
 *
 * @author alexnieblas
 */
public class UsuarioMapper {
    public Usuario toEntity(UsuarioNDTO usuarioDTO){
        return new Usuario(usuarioDTO.getId() ,usuarioDTO.getNombre_usuario(), usuarioDTO.getContraseniaUsuario(), usuarioDTO.getTipo_usuario());
    }
    
    public UsuarioNDTO toDTO(Usuario usuario){
        return new UsuarioNDTO(usuario.getIdUsuario() ,usuario.getNombre_usuario(), usuario.getContraseniaUsuario(), usuario.getTipo_usuario());
    }
}