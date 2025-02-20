package com.mycompany.persistencia;

import DAO.CitaDAO;
import DAO.DireccionDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import Exception.PersistenciaException;
import conexion.ConexionBD;
import conexion.IConexionBD;
import entidades.Cita;
import entidades.Direccion;
import entidades.Paciente;
import entidades.Usuario;
import java.sql.Date;

/**
 *
 * @author alexnieblas
 */
public class Persistencia {

    public static void main(String[] args) {
        
        IConexionBD conexionBD = new ConexionBD();
        PacienteDAO pacienteDAO = new PacienteDAO(conexionBD);
        MedicoDAO medicoDAO = new MedicoDAO(conexionBD);
        DireccionDAO direccionDAO = new DireccionDAO(conexionBD);

        // AÑADIR USUARIO Y PACIENTE
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre_usuario("ErnestoLpz_05");
            nuevoUsuario.setContraseniaUsuario("olalo");
            nuevoUsuario.setTipo_usuario("Paciente");

            Paciente nuevoPaciente = new Paciente();
            nuevoPaciente.setCorreoElectronicoPaciente("jesusE05@gmail.com");
            nuevoPaciente.setNombrePaciente("Ernesto");
            nuevoPaciente.setApellidoPaterno("Lopez");
            nuevoPaciente.setApellidoMateno("Ibarra");
            nuevoPaciente.setTelefono("6442291847");
            nuevoPaciente.setFechaNacPaciente(Date.valueOf("2005-08-12"));

            // Agregar Usuario y Paciente
            boolean exito = pacienteDAO.agregarUsuarioYPaciente(nuevoUsuario, nuevoPaciente);

            if (exito) {
                System.out.println("Usuario y paciente agregados con éxito.");

                // Asignar el idPaciente (por ejemplo, si es generado automáticamente)
                int idPaciente = nuevoPaciente.getIdUsuario(); // Asegúrate de que se asigne correctamente

                // Crear y agregar la dirección del paciente
                Direccion nuevaDireccion = new Direccion();
                nuevaDireccion.setId_Paciente(idPaciente);
                nuevaDireccion.setCalle("Av. Reforma");
                nuevaDireccion.setNumero("123");
                nuevaDireccion.setColonia("Centro");

                try {
                    direccionDAO.agregarDireccion(nuevaDireccion);
                    System.out.println("Dirección agregada con éxito.");
                } catch (PersistenciaException e) {
                    System.err.println("Error al agregar la dirección: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error al agregar usuario y paciente.");
            }
        } catch (PersistenciaException ex) {
            System.err.println("Error en la persistencia: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Ocurrió un error inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        // agregar cita porfavor q funcione (carita llorando)
        CitaDAO citaDAO = new CitaDAO(conexionBD);
        
        try {
            // 1. Agendar una cita
            Cita cita1 = new Cita(4, 3, Date.valueOf("2025-02-20"), "Pendiente");
            Cita citaAgendada = citaDAO.agendarCita(cita1);
            System.out.println("Cita agendada: " + (citaAgendada != null));
            
           Cita citaConsultada = citaDAO.consultarCitaPorID(10);
            if (citaConsultada != null) {
                System.out.println("ID Paciente: " + citaConsultada.getIdPaciente());
                System.out.println("ID Medico: " + citaConsultada.getIdMedico());
                System.out.println("Fecha y Hora: " + citaConsultada.getFecha());
                System.out.println("Estado: " + citaConsultada.getEstadoCita());
                System.out.println("Tipo de Cita: " + citaConsultada.getTipoCita());
            } else {
                System.out.println("Cita no encontrada.");
            }
            
            // 3. Cancelar una cita
            boolean citaCancelada = citaDAO.cancelarCita(9);
            System.out.println("Cita cancelada: " + citaCancelada);
            
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
    } 
}
