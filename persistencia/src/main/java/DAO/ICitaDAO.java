package DAO;

import Exception.PersistenciaException;
import entidades.Cita;
import entidades.Medico;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface ICitaDAO {
    /**
     * Agenda una cita normal (no de emergencia)    .
     */
    public Cita agendarCita() throws PersistenciaException;

    /**
     * Agenda una cita de emergencia.
     */
    public Cita agendarCitaEmergencia() throws PersistenciaException;

    /**
     * Desagenda una cita existente
     */
    public Cita desagendarCita() throws PersistenciaException;

    /**
     * Realiza una auditoría de una cita normal (no de emergencia)
     */
    public Cita AuditoriaCita() throws PersistenciaException;

    /**
     * Realiza una auditoría de una cita de emergencia
     */
    public Cita AuditoriaCitaEmergencia() throws PersistenciaException;
    
    public List<Medico> obtenerDoctoresDisponibles(String especialidad) throws PersistenciaException;
    
    public List<String> obtenerHorarioDisponible(int idMedico) throws PersistenciaException;
    
    public int validarDisponibilidad(int idMedico, LocalDateTime fecha) throws PersistenciaException;
    
    public Cita agendarCita(Cita cita) throws PersistenciaException;
    
    public boolean cancelarCita(int idCita) throws PersistenciaException;
    
    public boolean agendarCitaEmergencia(int idPaciente) throws PersistenciaException;
    
    public Cita consultarCitaPorID(int idCita) throws PersistenciaException;
}
