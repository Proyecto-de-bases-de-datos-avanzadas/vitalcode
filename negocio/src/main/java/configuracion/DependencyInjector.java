package configuracion;

import BO.PacienteBO;
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
}
