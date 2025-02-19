package DAO;

import Exception.PersistenciaException;
import entidades.Cita;

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
}
