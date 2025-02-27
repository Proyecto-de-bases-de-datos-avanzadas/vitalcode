package DAO;

/**
 *
 * @author ErnestoLpz_252663
 */
import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Usuario;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements IUsuarioDAO {

    private IConexionBD conexion;

    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    
    //Agregar usuario
    public Usuario agregarUsuario(Usuario usuario) throws PersistenciaException {
        String sentenciaSQL = "{CALL AgregarUsuario(?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion();
                CallableStatement cs = conn.prepareCall(sentenciaSQL)) {
            cs.setString(1, usuario.getNombre_usuario());
            cs.setString(2, usuario.getContraseniaUsuario());
            cs.setString(3, usuario.getTipo_usuario());
            cs.registerOutParameter(4, Types.INTEGER);

            cs.execute();

            int idGenerado = cs.getInt(4);
            if (idGenerado > 0) {
                usuario.setIdUsuario(idGenerado);
                return usuario;
            } else {
                throw new PersistenciaException("No se pudo obtener el ID generado.");
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregar el usuario: " + e.getMessage(), e);
        }
    }

    //Consultar al usuario por su id
    @Override
    public Usuario consultarUsuarioPorID(int idUsuario) throws PersistenciaException {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try (Connection conn = conexion.crearConexion();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id"));
                    usuario.setNombre_usuario(rs.getString("nombreUsuario"));
                    usuario.setContraseniaUsuario(rs.getString("contraseña"));
                    usuario.setTipo_usuario(rs.getString("tipoUsuario"));
                    return usuario;
                } else {
                    throw new PersistenciaException("Usuario no encontrado");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al consultar usuario", ex);
        }
    }
    
    //Consultar al usuario por su nombre
    public Usuario buscarUsuarioPorUsuario(String usuario) throws PersistenciaException {
        String sql = "SELECT * FROM Usuario WHERE nombreUsuario = ?";
        Usuario usuarioRec = null;
        try (Connection conn = conexion.crearConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuarioRec = new Usuario();
                    usuarioRec.setIdUsuario(rs.getInt("id"));
                    usuarioRec.setNombre_usuario(rs.getString("nombreUsuario"));
                    usuarioRec.setContraseniaUsuario(rs.getString("contrasenia"));
                    usuarioRec.setTipo_usuario(rs.getString("tipoUsuario"));
                    return usuarioRec;
                } else {
                    throw new PersistenciaException("Usuario no encontrado");
                } 
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Error al consultar usuario", ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al crear conexión", ex);
        }
    }
}