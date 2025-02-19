package DAO;

import Exception.PersistenciaException;
import entidades.Cita;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class CitaDAO {
    private Connection conexion;
    
    public CitaDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public Cita agendarCita(int idPaciente, int idMedico, Date fechaHora, String estadoCita, int folio, String tipoCita) throws PersistenciaException {
        String sentenciaSQL = "INSERT INTO CITA (idPaciente, idMedico, fechaHora, estado, folio, tipoCita) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idPaciente);
            ps.setInt(2, idMedico);
            ps.setDate(3, fechaHora);
            ps.setString(4, estadoCita);
            ps.setInt(5, folio);
            ps.setString(6, tipoCita);
            
            int filasAf = ps.executeUpdate();
            if (filasAf > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idCita = rs.getInt(1);
                        Cita cita = new Cita(idCita, idPaciente, idMedico, fechaHora, estadoCita, folio, tipoCita);
                        AuditoriaCita(idCita, "Agendada", tipoCita, idPaciente, idMedico);
                        return cita;
                    }
                }
            }
            throw new PersistenciaException("Error al agendar la cita.");
        } catch (SQLException ex) {
            Logger.getLogger(CitaDAO.class.getName()).log(Level.SEVERE, "Error al agendar cita", ex);
            throw new PersistenciaException("Error al agendar la cita: " + ex.getMessage());
        }
    }
    
    private Date obtenerProximaCita(int idMedico) throws PersistenciaException {//Terminar :(
        String consultaFechaDisponible = "";
        return null;
    }
    
    public Cita agendarCitaEmergencia(int idPaciente, int idMedico) throws PersistenciaException {
        int folioGenerado = (int) generarFolio();

        String sentenciaSQL = "INSERT INTO CITA (idPaciente, idMedico, fechaHora, estado, folio, tipoCita) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idPaciente);
            ps.setInt(2, idMedico);
            //ps.setDate(3, ); | Encontrar una forma para encontrar la cita/fecha mas cercana
            ps.setString(4, "Pendiente");
            ps.setInt(5, folioGenerado);
            ps.setString(6, "Emergencia");

            int filasAf = ps.executeUpdate();
            if (filasAf > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idCita = rs.getInt(1);
                        Cita cita = new Cita(idCita, idPaciente, idMedico, TERMINAR_FECHA_MAS_CERCANA, "Pendiente", folioGenerado, "Emergencia");
                        AuditoriaCita(idCita, "Agendada", "Emergencia", idPaciente, idMedico);
                        return cita;
                    }
                }
            }
            throw new PersistenciaException("Error al agendar la cita de emergencia.");
        } catch (SQLException ex) {
            Logger.getLogger(CitaDAO.class.getName()).log(Level.SEVERE, "Error al agendar cita de emergencia", ex);
            throw new PersistenciaException("Error al agendar la cita de emergencia: " + ex.getMessage());
        }
    }
    
    public void desagendarCita(int idCita) throws PersistenciaException {
        String sentenciaSQL = "DELETE FROM CITA WHERE idCita = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL)) {
            ps.setInt(1, idCita);

            int filasAf = ps.executeUpdate();
            if (filasAf == 0) {
                throw new PersistenciaException("No se encontró la cita con ID: " + idCita);
            }

            AuditoriaCita(idCita, "Cancelada", "", 0, 0);
            } catch (SQLException ex) {
            Logger.getLogger(CitaDAO.class.getName()).log(Level.SEVERE, "Error al desagendar cita", ex);
            throw new PersistenciaException("Error al desagendar cita: " + ex.getMessage());
        }
    }
    
    public Cita AuditoriaCita(int idCita, String estado, String tipoCita, int idPaciente, int idMedico) throws PersistenciaException {
        String sentenciaSQL = "INSERT INTO AUDITORIA_CITA (idCita, fechaHora, estado, tipo, id_paciente, id_medico, folio) VALUES (?, NOW(), ?, ?, ?, ?, ?)";

        int folioGenerado = (int) generarFolio();

        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL)) {
            ps.setInt(1, idCita);
            ps.setString(2, estado);
            ps.setString(3, tipoCita);
            ps.setInt(4, idPaciente);
            ps.setInt(5, idMedico);
            ps.setInt(6, folioGenerado);

            ps.executeUpdate();

            Cita cita = new Cita();
            cita.setIdCita(idCita);
            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(idMedico);
            cita.setFecha(new Date(System.currentTimeMillis()));
            cita.setEstadoCita(estado);
            cita.setFolioCita(folioGenerado);
            cita.setTipoCita(tipoCita);

            return cita;
        } catch (SQLException ex) {
            Logger.getLogger(CitaDAO.class.getName()).log(Level.SEVERE, "Error al registrar auditoría de cita", ex);
            throw new PersistenciaException("Error al registrar auditoría de cita: " + ex.getMessage());
        }
    }

    public Cita obtenerCitaPorId(int idCita) throws PersistenciaException {
        String sentenciaSQL = "SELECT * FROM CITAS WHERE idCita = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL)) {
            ps.setInt(1, idCita);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("idCita"));
                    cita.setIdPaciente(rs.getInt("idPaciente"));
                    cita.setIdMedico(rs.getInt("idMedico"));
                    cita.setFecha(rs.getDate("fecha"));
                    cita.setEstadoCita(rs.getString("estado"));
                    cita.setFolioCita(rs.getInt("folio"));
                    cita.setTipoCita(rs.getString("tipoCita"));
                    
                    return cita;
                }else{
                    throw new PersistenciaException("No se encontró la cita con el ID: " + idCita);
                }
            }
        } catch (SQLException ex) {     
            Logger.getLogger(CitaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private long generarFolio() {
        return (long) (Math.random() * 100000000);
    }
}