package entidades;

/**
 *
 * @author erika
 */
public class Usuario {
    private int idUsuario;
    private String nombre_usuario; 
    private String contraseniaUsuario; 
    private String tipo_usuario;

    public Usuario(String nombre_usuario, String contraseniaUsuario) {
        this.nombre_usuario = nombre_usuario;
        this.contraseniaUsuario = contraseniaUsuario;
    }

    public Usuario() {
    }

    public Usuario(String nombre_usuario, String contraseniaUsuario, String tipo_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.tipo_usuario = tipo_usuario;
    }

    public Usuario(int idUsuario, String nombre_usuario, String contraseniaUsuario, String tipo_usuario) {
        this.idUsuario = idUsuario;
        this.nombre_usuario = nombre_usuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.tipo_usuario = tipo_usuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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