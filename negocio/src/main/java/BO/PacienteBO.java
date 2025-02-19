package BO;

import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import Exception.NegocioException;
import conexion.IConexionBD;
import java.sql.Connection;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteBO {
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final NegocioException mapper = new NegocioException("");
    private final IPacienteDAO pacienteDAO;
    
    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = (IPacienteDAO) new PacienteDAO((Connection) conexion);
    }
    
}