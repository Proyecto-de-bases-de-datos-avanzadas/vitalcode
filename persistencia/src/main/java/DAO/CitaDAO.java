package DAO;

import Exception.PersistenciaException;
import conexion.IConexionBD;
import entidades.Cita;
import entidades.Medico;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErnestoLpz_252663
 */
public class CitaDAO {
    private IConexionBD conexion;
    
    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    
    //agrgar cita

    // vamos a separarlo y a usar este
    public int validarDisponibilidad(int idMedico, LocalDateTime fecha) throws PersistenciaException {
        String sqlValidacion = "CALL VerificarDisponibilidadMedico(?,?)";

        try (Connection conn = conexion.crearConexion();
                CallableStatement cs = conn.prepareCall(sqlValidacion)) {

            cs.setInt(1, idMedico);
            cs.setTimestamp(2, Timestamp.valueOf(fecha));

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    int disponibilidad = rs.getInt(1); // Leer el resultado
                    System.out.println("Resultado SQL: " + disponibilidad);
                    return disponibilidad;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al validar disponibilidad: " + e.getMessage(), e);
        }

        return 0; // Si no devuelve nada, asumimos que el médico NO está disponible
    }
    
    public Cita agendarCita(Cita cita) throws PersistenciaException {
        String sqlValidacion = "CALL VerificarDisponibilidadMedico(?,?)";
        String sqlCita = "{CALL agregar_cita(?, ?, ?, ?, ?, ?)}";
        int disponibilidad = 0;
        try (Connection conn = conexion.crearConexion()) {
            conn.setAutoCommit(false);

            try (CallableStatement cs = conn.prepareCall(sqlValidacion)) {
                System.out.println(cita.getIdMedico());
                System.out.println(Timestamp.valueOf(cita.getFecha()).toString());  // Verifica el valor de la fecha

                // AsegÃºrate de pasar el Timestamp correctamente
                cs.setInt(1, 2);
                cs.setTimestamp(2, Timestamp.valueOf(cita.getFecha()));

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {

                        // Obtener el valor que devuelve el procedimiento
                        disponibilidad = rs.getInt(1);  // El primer valor de la primera columna
                        System.out.println("Resultado de la validaciÃ³n: " + disponibilidad);  // AsegÃºrate de ver el valor que se obtiene
                    }
                }
            }

            if (disponibilidad == 0) {
                throw new PersistenciaException("Error: El médico NO está disponible en este horario.");
            }

            // 2. Si estÃ¡ disponible, se aÃ±ade la cita
            try (CallableStatement csCita = conn.prepareCall(sqlCita)) {
                csCita.setInt(1, cita.getIdPaciente());
                csCita.setInt(2, cita.getIdMedico());
                csCita.setTimestamp(3, Timestamp.valueOf(cita.getFecha()));
                csCita.setString(4, cita.getEstadoCita());
                csCita.setInt(5, cita.getFolioCita());
                csCita.setString(6, cita.getTipoCita());

                int filasInsertadas = csCita.executeUpdate();

                if (filasInsertadas <= 0) {
                    conn.rollback();
                    throw new PersistenciaException("No se pudo registrar la cita.");
                }
            }

            conn.commit();
            return cita;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al registrar la cita: " + e.getMessage(), e);
        }
    }
     
        // Cancelar cita (cambiar estado a 'Cancelada')
    public boolean cancelarCita(int idCita) throws PersistenciaException {
        String sql = "UPDATE Cita SET estado = 'Cancelada' WHERE id = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCita);

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al cancelar la cita: " + e.getMessage(), e);
        }
    }
    
    //???
    public boolean agendarCitaEmergencia(int idPaciente) throws PersistenciaException {
        String sql = "{CALL AsignarCitaEmergencia(?)}";

        try (Connection conn = conexion.crearConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPaciente);
            boolean resultado = cs.execute();

            // Obtener el mensaje del procedimiento almacenado
            ResultSet rs = cs.getResultSet();
            if (rs.next()) {
                System.out.println(rs.getString("mensaje"));
            }

            return resultado;
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al asignar cita de emergencia.", ex);
        }
    }
    
    public List<Medico> obtenerDoctoresDisponibles(String especialidad) throws PersistenciaException {
        List<Medico> doctores = new ArrayList<>();
        String sql = "SELECT idUsuario, nombre, especialidad, cedula, estado FROM Medicos WHERE especialidad = ? AND estado = 'Disponible'";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medico medico = new Medico(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("especialidad"),
                        rs.getString("cedula"),
                        rs.getString("estado")
                    );
                    doctores.add(medico);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener doctores disponibles", e);
        }

        return doctores;
    }
    
    public List<String> obtenerHorarioDisponible(int idMedico) throws PersistenciaException {
        List<String> horarios = new ArrayList<>();
        String sql = "SELECT horario FROM Horarios WHERE idMedico = ? AND disponible = 1";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ps.setInt(1, idMedico);

            while (rs.next()) {
                horarios.add(rs.getString("horario"));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener horarios disponibles", e);
        }
        return horarios;
    }
 // Consultar cita por ID
    public Cita consultarCitaPorID(int idCita) throws PersistenciaException {
    String sql = "SELECT * FROM Cita WHERE id = ?";
    try (Connection conn = conexion.crearConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idCita);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Cita(
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getObject("fechaHora", LocalDateTime.class),
                    rs.getString("estado"),
                    rs.getString("tipoDeCita")
                );
            } else {
                throw new PersistenciaException("Cita no encontrada.");
            }
        }
    } catch (SQLException e) {
        throw new PersistenciaException("Error al consultar la cita: " + e.getMessage(), e);
    }
    }
  
}