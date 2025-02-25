package BO;

import DAO.ConsultaDAO;
import Exception.PersistenciaException;

import entidades.Consulta;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_25263
 */
public class ConsultaBO {
    
    private final ConsultaDAO consultaDAO;
    
    public ConsultaBO(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }
    
    public boolean agregarConsulta(int idCita, String tipoDeConsulta, LocalDateTime fechaHora, String diagnostico, String notas) {
        try {
            Date fechaSQL = Date.valueOf(fechaHora.toLocalDate());
            
            return consultaDAO.agregarConsulta(idCita, tipoDeConsulta, fechaSQL, diagnostico, notas);
        } catch (PersistenciaException ex) {
            Logger.getLogger(ConsultaBO.class.getName()).log(Level.SEVERE, "Error al agregar consulta", ex);
            return false;
        }
    }
    
    public boolean eliminarConsulta(int idConsulta) {
        try {
            return consultaDAO.eliminarConsulta(idConsulta);
        } catch (PersistenciaException ex) {
            Logger.getLogger(ConsultaBO.class.getName()).log(Level.SEVERE, "Error al eliminar consulta", ex);
            return false;
        }
    }
    
    public List<Consulta> obtenerConsultasPorMedico(int idMedico) {
        try {
            return consultaDAO.obtenerConsultasPorMedico(idMedico);
        } catch (PersistenciaException ex) {
            Logger.getLogger(ConsultaBO.class.getName()).log(Level.SEVERE, "Error al obtener consultas del médico", ex);
            return null;
        }
    }
}
