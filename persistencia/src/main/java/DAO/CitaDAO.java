package DAO;

import Exception.PersistenciaException;
import entidades.Cita;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
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
    public Cita agendarCita(int idPaciente, Date fechaHora, String consulta) throws PersistenciaException{
    String sentenciaSQL = "INSERT INTO CITA (idPaciente, fechaHora, consulta) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idPaciente);
            ps.setDate(2, fechaHora);
            ps.setString(4, consulta);
            //id, id_paciente, id_medico, fechaHora, estado, folio, tipoDeCita | ATRIBUTOS CITA
            int filasAf = ps.executeUpdate();
            if (filasAf > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idCita = generatedKeys.getInt(1);
                    Cita cita = new Cita(idCita, idPaciente, idMedico, fecha, estadoCita, folio, tipoCita); //Solucionar error con el constructor
                    AuditoriaCita(idCita, "Agendada");
                    return cita;
                }
            }
            throw new PersistenciaException("Error al agendar la cita.");
        } catch (SQLException ex) {
            Logger.getLogger(CitaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al agendar la cita: " + ex.getMessage());
        }
    }
    
//    public Cita agendarCitaEmergencia() throws PersistenciaException{
//        
//    }
//    
//    public Cita desagendarCita() throws PersistenciaException{
//        
//    }
//    
    public Cita AuditoriaCita(int idCita, String accion) throws PersistenciaException {//Verificar funcionamiento
        String sentenciaSQL = "INSERT INTO AUDITORIA_CITA (id, fechaHora, estado, tipoMovimiento, tipo, id_paciente, id_medico) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sentenciaSQL)) {
            //añadir los ps.setInt o string
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CitaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al registrar auditoría de cita: " + ex.getMessage());
        }
        return null;
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
}