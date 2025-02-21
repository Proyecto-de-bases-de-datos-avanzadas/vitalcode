/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author alexnieblas
 */
public class UsuarioNDTO {
    private String nombre_usuario; 
    private String contraseniaUsuario; 
    private String tipo_usuario;
    private int id;

    public UsuarioNDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioNDTO(int id,String nombre_usuario, String contraseniaUsuario, String tipo_usuario ) {
        this.nombre_usuario = nombre_usuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.tipo_usuario = tipo_usuario;
        this.id = id;
    }

   

    public UsuarioNDTO(String nombre_usuario, String contraseniaUsuario, String tipo_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.tipo_usuario = tipo_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContraseniaUsuario() {
        return contraseniaUsuario;
    }

    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = contraseniaUsuario;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
