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

    public static void main(String[] args) throws PersistenciaException {
        
        IConexionBD conexionBD = new ConexionBD();
        PacienteDAO pacienteDAO = new PacienteDAO(conexionBD);
        MedicoDAO medicoDAO = new MedicoDAO(conexionBD);
        DireccionDAO direccionDAO = new DireccionDAO(conexionBD);
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
        CitaDAO citaDAO = new CitaDAO(conexionBD);
        
        // prueba recuperar direecion por id
        try{
            Direccion direccion = direccionDAO.obtenerDireccionPorIdPaciente(17);
            if(direccion!=null){
                System.out.println("Direccion encontrada");
                System.out.println("Calle: "+direccion.getCalle());
            }
        }catch (PersistenciaException e) {
            e.printStackTrace();
            }

        // prueba recuperar paciente por id
        try{
            Paciente paciente = pacienteDAO.consultarPacientePorID(17);
            if(paciente!=null){
                System.out.println("Paciente encontrado");
                System.out.println("Nombre: "+paciente.getNombrePaciente());
            }
        }catch (PersistenciaException e) {
            e.printStackTrace();
        }
        // prueba recuperar usuario por nombre
        try {
            Usuario usuario = usuarioDAO.buscarUsuarioPorUsuario("lucia123");
            if (usuario != null) {
                System.out.println("Usuario encontrado:");
                System.out.println("ID: " + usuario.getIdUsuario());
                System.out.println("Nombre de Usuario: " + usuario.getNombre_usuario());
                System.out.println("Contraseña: " + usuario.getContraseniaUsuario());
                System.out.println("Tipo de Usuario: " + usuario.getTipo_usuario());
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
        
        // AÑADIR USUARIO Y PACIENTE
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre_usuario("Luci");
            nuevoUsuario.setContraseniaUsuario("miisis");
            nuevoUsuario.setTipo_usuario("Paciente");

            Paciente nuevoPaciente = new Paciente();
            nuevoPaciente.setCorreoElectronicoPaciente("lucia1111@gmail.com");
            nuevoPaciente.setNombrePaciente("Lucia");
            nuevoPaciente.setApellidoPaterno("Vasquez");
            nuevoPaciente.setApellidoMateno("Gastelum");
            nuevoPaciente.setTelefono("64424235");
            nuevoPaciente.setFechaNacPaciente(Date.valueOf("2005-07-05"));

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
        
        // prueba recuperar usuario por nombre
        try {
            Usuario usuario = usuarioDAO.buscarUsuarioPorUsuario("lucia123");
            if (usuario != null) {
                System.out.println("Usuario encontrado:");
                System.out.println("ID: " + usuario.getIdUsuario());
                System.out.println("Nombre de Usuario: " + usuario.getNombre_usuario());
                System.out.println("Contraseña: " + usuario.getContraseniaUsuario());
                System.out.println("Tipo de Usuario: " + usuario.getTipo_usuario());
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
        
        // AÑADIR USUARIO Y PACIENTE
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre_usuario("Luci");
            nuevoUsuario.setContraseniaUsuario("miisis");
            nuevoUsuario.setTipo_usuario("Paciente");

            Paciente nuevoPaciente = new Paciente();
            nuevoPaciente.setCorreoElectronicoPaciente("lucia1111@gmail.com");
            nuevoPaciente.setNombrePaciente("Lucia");
            nuevoPaciente.setApellidoPaterno("Vasquez");
            nuevoPaciente.setApellidoMateno("Gastelum");
            nuevoPaciente.setTelefono("64424235");
            nuevoPaciente.setFechaNacPaciente(Date.valueOf("2005-07-05"));

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
        
        //Pruebas citas:
        
        Cita nuevaCita = new Cita();
        nuevaCita.setIdPaciente(1);
        nuevaCita.setIdMedico(3);
        nuevaCita.setFecha(Date.valueOf("2025-03-20"));
        nuevaCita.setEstadoCita("Pendiente");
        nuevaCita.setTipoCita("Regular");
        
        // 1. Agendar una cita
        try {
            Cita citaAgendada = citaDAO.agendarCita(nuevaCita);
            if (citaAgendada != null) {
                System.out.println("Cita agendada con éxito: " + citaAgendada.getIdCita());
                } else {
                    System.out.println("Error al agendar la cita.");
                }
            } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        // 2. Consultar una cita por ID
        Cita citaConsultada = citaDAO.consultarCitaPorID(1);
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
        
        //Cita emergencia
        try {
            int idPaciente = 4;

            boolean citaEmergenciaAgendada = citaDAO.agendarCitaEmergencia(idPaciente);

            if (citaEmergenciaAgendada) {
                System.out.println("Cita de emergencia agendada con éxito.");
            } else {
                System.out.println("Error al agendar la cita de emergencia.");
            }

        } catch (PersistenciaException e) {
            System.err.println("Error al agendar cita de emergencia: " + e.getMessage());
            e.printStackTrace();
        }


    } 
}
