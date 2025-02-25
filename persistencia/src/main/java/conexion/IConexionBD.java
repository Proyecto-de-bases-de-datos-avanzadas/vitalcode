package conexion;

import Exception.PersistenciaException;
import java.sql.Connection;

/**
 *
 * @author erika
 */
public interface IConexionBD {
     public Connection crearConexion() throws PersistenciaException;
}
