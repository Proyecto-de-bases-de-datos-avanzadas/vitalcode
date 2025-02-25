package DAO;

import Exception.PersistenciaException;
import java.sql.Date;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IConsultaDAO {
    public boolean agregarConsulta(int idCita, String tipoDeConsulta, Date fechaHora, String diagnostico, String notas) throws PersistenciaException;
    
    public boolean eliminarConsulta(int idConsulta) throws PersistenciaException;
}
