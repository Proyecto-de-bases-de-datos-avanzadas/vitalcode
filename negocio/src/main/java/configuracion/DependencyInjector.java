package configuracion;

import BO.DireccionBO;
import BO.MedicoBO;
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
    public static PacienteBO actualizarPaciente() {
        IConexionBD conexion = new ConexionBD();
        return new PacienteBO(conexion);
    }
    public static UsuarioBO consultarUsuario(){
        IConexionBD conexion = new ConexionBD();
        UsuarioBO usuarioBO = new UsuarioBO(conexion);
        return usuarioBO;
        
    }
    public static DireccionBO consultarDireccion(){
        IConexionBD conexion = new ConexionBD();
        DireccionBO direccionBO = new DireccionBO(conexion);
        return direccionBO;
    }
    public static MedicoBO consultarMedico(){
        IConexionBD conexion = new ConexionBD();
        MedicoBO medicoBO = new MedicoBO(conexion);
        return medicoBO;
    }
}
