/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.UsuarioNDTO;
import entidades.Usuario;

/**
 *
 * @author alexnieblas
 */
public class UsuarioMapper {
    public Usuario toEntity(UsuarioNDTO usuarioDTO){
        return new Usuario(usuarioDTO.getNombre_usuario(), usuarioDTO.getContraseniaUsuario(), usuarioDTO.getTipo_usuario());
    }
    
    public UsuarioNDTO toDTO(Usuario usuario){
        return new UsuarioNDTO(usuario.getNombre_usuario(), usuario.getContraseniaUsuario(), usuario.getTipo_usuario());
    }
}
