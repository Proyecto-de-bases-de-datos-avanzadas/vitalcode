package BO;

import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNDTO;
import DTO.PacienteVDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.PacienteMapper;
import conexion.IConexionBD;
import entidades.Paciente;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class PacienteBO {
    
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IPacienteDAO pacienteDAO;
    private final PacienteMapper mapper = new PacienteMapper(); // Usamos el mapper
    private PacienteNDTO pacienteNuevo;

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = (IPacienteDAO) new PacienteDAO((Connection) conexion);
    }
    
    public boolean agregarPaciente(PacienteNDTO pacienteNuevo) throws NegocioException {
        if (pacienteNuevo == null) {
            throw new NegocioException("El paciente no puede ser nulo.");
        }

        if (pacienteNuevo.getNombre().isEmpty() || pacienteNuevo.getApellidoPaterno().isEmpty()
                || pacienteNuevo.getTelefono().isEmpty()) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }
        return false;
    }
}