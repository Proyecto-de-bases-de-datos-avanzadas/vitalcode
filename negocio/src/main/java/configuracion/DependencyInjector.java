package configuracion;

import BO.PacienteBO;
import BO.UsuarioBO;
import conexion.ConexionBD;
import conexion.IConexionBD;

/**
 *
 * @author ErnestoLpz_252663
 */
public class DependencyInjector {
    public static PacienteBO crearPacienteBO(){
        IConexionBD conexion = new ConexionBD();
        
        PacienteBO pacienteBO = new PacienteBO(conexion);
        
        return pacienteBO;
    }
    public static UsuarioBO consultarUsuario(){
        IConexionBD conexion = new ConexionBD();
        UsuarioBO usuarioBO = new UsuarioBO(conexion);
        return usuarioBO;
        
    }
}
