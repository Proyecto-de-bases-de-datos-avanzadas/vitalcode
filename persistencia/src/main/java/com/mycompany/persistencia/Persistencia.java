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
import entidades.Horario;
import entidades.Medico;
import entidades.Paciente;
import entidades.Usuario;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexnieblas
 */
public class Persistencia {

    public static void main(String[] args) throws PersistenciaException, ParseException {
        
            IConexionBD conexionBD = new ConexionBD();
            PacienteDAO pacienteDAO = new PacienteDAO(conexionBD);
            DireccionDAO direccionDAO = new DireccionDAO(conexionBD);
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
            CitaDAO citaDAO = new CitaDAO(conexionBD);
            
            
            // 1. Crear listas para usuarios y médicos
        List<Usuario> usuarios = new ArrayList<>();
        List<Medico> medicos = new ArrayList<>();

        // 2. Crear médicos con diferentes especialidades (aggare al azar)
        String[] nombres = {"Dr. Juan", "Dra. Ana", "Dr. Luis", "Dra. María", "Dr. Carlos"};
        String[] especialidades = {"Cardiología", "Pediatría", "Neurología", "Ortopedia", "Dermatología"};
        String[] cedulas = {"12345", "67890", "54321", "98765", "56789"};
        String[] usuariosNombres = {"usuario1", "usuario2", "usuario3", "usuario4", "usuario5"};
        String[] contraseñas = {"pass1", "pass2", "pass3", "pass4", "pass5"};

        // 3. Generar médicos, usuarios y asignar horarios de lunes a viernes para to2 los médicos
        for (int i = 0; i < 5; i++) {
            // Crear usuario
            Usuario usuario = new Usuario(usuariosNombres[i], contraseñas[i], "Medico");
            usuarios.add(usuario);

            // Crear horarios de lunes a viernes con diferentes horas
//            List<Horario> horarios = new ArrayList<>();
//            horarios.add(new Horario("Monday", Time.valueOf("08:00:00"), Time.valueOf("14:00:00")));
//            horarios.add(new Horario("Tuesday", Time.valueOf("09:00:00"), Time.valueOf("15:00:00")));
//            horarios.add(new Horario("Wednesday", Time.valueOf("10:00:00"), Time.valueOf("16:00:00")));
//            horarios.add(new Horario("Thursday", Time.valueOf("11:00:00"), Time.valueOf("17:00:00")));
//            horarios.add(new Horario("Friday", Time.valueOf("12:00:00"), Time.valueOf("18:00:00")));

            // Crear médico
//            Medico medico = new Medico(nombres[i], especialidades[i], cedulas[i], "Activo", horarios);
//            medicos.add(medico);
        }

        // Crear DAO y llamar al método de inserción masiva de la DAO
        MedicoDAO medicoDAO = new MedicoDAO(new ConexionBD());

        try {
            boolean resultado = medicoDAO.agregarMedicosConHorarios(medicos, usuarios);
            System.out.println("Inserción masiva completada: " + resultado);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
        
            // Consultar todas las citas de un médico por fecha
            List<Cita> todasLasCitasFecha = medicoDAO.obtenerTodasLasCitasPorFecha(5);
            System.out.println("Todas las citas del médico:");
            for (Cita cita : todasLasCitasFecha) {
                System.out.println(cita.getFecha());
            }
            if(todasLasCitasFecha.isEmpty()){
                System.out.println("no hay citas");
            }
            
            // Consultar todas las citas de un médico por estado
            List<Cita> todasLasCitasEstado = medicoDAO.obtenerTodasLasCitasPorEstado(5);
            System.out.println("Todas las citas del médico:");
            for (Cita cita : todasLasCitasEstado) {
                System.out.println(cita.getFecha());
            }
            if(todasLasCitasEstado.isEmpty()){
                System.out.println("no hay citas");
            }
            
            // Consultar todas las citas de un médico
            List<Cita> todasLasCitas = medicoDAO.obtenerTodasLasCitas(5);
            System.out.println("Todas las citas del médico:");
            for (Cita cita : todasLasCitas) {
                System.out.println(cita.getFecha());
            }
            if(todasLasCitas.isEmpty()){
                System.out.println("no hay citas");
            }
            //consultar citas pendientes medico
            List<Cita> todasLasCitasP = medicoDAO.obtenerTodasLasCitasPendientes(5);
            System.out.println("Todas las citas pendientes:");
            for (Cita cita : todasLasCitasP) {
                System.out.println(cita.getFecha()+" "+cita.getEstadoCita());
            }
            if(todasLasCitas.isEmpty()){
                System.out.println("no hay citas");
            }
            
            //recuperar medico por id
            int idMedico = 5;
            Medico medicoRecuperado = medicoDAO.consultarMedicoID(idMedico);
            System.out.println("nombre Medico: "+medicoRecuperado.getNombre());
            System.out.println("Especialidad: "+medicoRecuperado.getEspecialidadMedico());

        //Agregar una nueva cita
//            Cita nuevaCita = new Cita();
//            nuevaCita.setIdPaciente(37);
//            nuevaCita.setIdMedico(3);
//            nuevaCita.setFecha(Timestamp.valueOf("2025-03-01 10:00:00"));
//            nuevaCita.setEstadoCita("Pendiente");
//            nuevaCita.setFolioCita(12345);
//            nuevaCita.setTipoCita("Regular");
//
//            try {
//            citaDAO.agendarCita(nuevaCita);
//            System.out.println("Cita agregada exitosamente.");
//            } catch (PersistenciaException e) {
//            System.err.println("Error al agregar la cita: " + e.getMessage());
//            }

            // Consultar todas las citas de un paciente
            try {
            List<Cita> citas = pacienteDAO.obtenerTodasLasCitas(1);
            System.out.println("Citas del paciente:");
            for (Cita cita : citas) {
            System.out.println(cita);
            }
            } catch (SQLException e) {
            System.err.println("Error al consultar las citas: " + e.getMessage());
            }
            
            //probar metodo dar de baja medico
            int idMedicoBaja = 77;
            boolean exitoBaja = medicoDAO.darDeBajaMedico(idMedicoBaja);
            if(exitoBaja==true){
            System.out.println("Medico Inactivo");
            }
            else{
            System.out.println("no se pudo gg");
            }
            
            boolean exitoAlta = medicoDAO.activarMedico(idMedicoBaja);
            if(exitoAlta==true){
            System.out.println("Medico Activo");
            }else{
            System.out.println("no se pudox2");
            }
            try {
            // Probar el método getDoctoresDisponibles
            List<Medico> doctores = citaDAO.obtenerDoctoresDisponibles("Cardiología");
            System.out.println("Doctores disponibles en Cardiología:");
            for (Medico doctor : doctores) {
            System.out.println("ID: " + doctor.getIdUsuario() + ", Nombre: " + doctor.getNombre() + ", Especialidad: " + doctor.getEspecialidadMedico()+ ", Cédula: " + doctor.getCedulaMedico()+ ", Estado: " + doctor.getEstadoMedico());
            
            }
            } catch (PersistenciaException e) {
            e.printStackTrace();
            }
            
            try {
            // Probar el método getHorarioDisponible
            int idMedico2 = 3; 
            List<String> horarios = citaDAO.obtenerHorarioDisponible(idMedico2);

            System.out.println("Horarios disponibles para el doctor con ID " + idMedico + ":");
            for (String horario : horarios) {
            System.out.println(horario);
            }
            } catch (PersistenciaException e) {
            e.printStackTrace();
            }
            
            try {
            // Crear un nuevo Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre_usuario("dr.smith");
            nuevoUsuario.setContraseniaUsuario("password123");
            nuevoUsuario.setTipo_usuario("Medico");

            // Crear un nuevo Medico
            Medico nuevoMedico = new Medico();
            nuevoMedico.setNombre("Dr. John Smith");
            nuevoMedico.setEspecialidadMedico("Cardiología");
            nuevoMedico.setCedulaMedico("67890DEF");
            nuevoMedico.setEstadoMedico("Activo");

            // Llamar al método agregarMedicoYUsuario
            boolean exito = medicoDAO.agregarMedicoYUsuario(nuevoMedico, nuevoUsuario);
            if (exito) {
            System.out.println("Médico y usuario agregados exitosamente.");
            } else {
            System.out.println("Error al agregar el médico y el usuario.");
            }
            } catch (PersistenciaException e) {
            e.printStackTrace();
            }
            // Llamar al método consultarMedicoPorNombre
            Medico medico = medicoDAO.consultarMedicoPorNombre("dr.juarez");
            
            // Imprimir los detalles del médico recuperado
            System.out.println("ID Usuario: " + medico.getIdUsuario());
            System.out.println("Nombre: " + medico.getNombre());
            System.out.println("Especialidad: " + medico.getEspecialidadMedico());
            System.out.println("Cedula: " + medico.getCedulaMedico());
            System.out.println("Estado: " + medico.getEstadoMedico());
            
            
            
            try {
            // Llamar al método consultarHorarioMedico
            List<Horario> horarios = medicoDAO.consultarHorarioMedico(3);
            
            if (!horarios.isEmpty()) {
            // Imprimir los detalles del horario recuperado
            for (Horario horario : horarios) {
            System.out.println("ID Horario: " + horario.getId());
            System.out.println("Día de la Semana: " + horario.getDiaSemana());
            System.out.println("Hora de Entrada: " + horario.getHoraEntrada());
            System.out.println("Hora de Salida: " + horario.getHoraSalida());
            System.out.println("-----------------------------------");
            }
            } else {
            System.out.println("No se encontró ningún horario para el médico con ese ID.");
            }
            } catch (PersistenciaException e) {
            e.printStackTrace();
            }
            
            
            //recuperar medico por id
            int idMedico3 = 3;
            Medico medicoRecuperado2 = medicoDAO.consultarMedicoID(idMedico3);
            System.out.println("nombre Medico: "+medicoRecuperado2.getNombre());
            
            
            // modificar direccion
            Direccion direccion1 = new Direccion(5, "calle1133", "245", "Colonia3525");
            boolean exito1 = direccionDAO.actualizarDireccion(direccion1);
            if(exito1){
            System.out.println("direccion actualizada");
            }else{
            System.out.println("No se pudo actualizar la direccion");
            }
            //prueba modificar paciente
            Paciente paciente = new Paciente();
            paciente.setIdUsuario(17); // ID del paciente a actualizar
            paciente.setCorreoElectronicoPaciente("nuevo_correo@ejemplo.com");
            paciente.setNombrePaciente("Nuevo Nombre");
            paciente.setApellidoPaterno("Nuevo Apellido Paterno");
            paciente.setApellidoMateno("Nuevo Apellido Materno");
            paciente.setTelefono("1234567890");
            paciente.setFechaNacPaciente(Date.valueOf("2005-07-05"));
            try {
            // Llamar al método actualizarPaciente
            boolean exito = pacienteDAO.ActualizarPaciente(paciente);
            if (exito) {
            System.out.println("Paciente actualizado exitosamente.");
            } else {
            System.out.println("Error al actualizar el paciente.");
            }

        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
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
            Paciente paciente1 = pacienteDAO.consultarPacientePorID(17);
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
        // 1. Agendar una cita
        try {
            LocalDateTime fecha = LocalDateTime.of(2025, 2, 21, 10, 0, 0);
            Cita nuevaCita6 = new Cita(3, 3, fecha, "Pendiente", "Regular");
            Cita citaAgendada = citaDAO.agendarCita(nuevaCita6); // Corrige nuevaCita

            if (citaAgendada != null) {
                LocalDateTime fecha7 = LocalDateTime.of(2025, 2, 28, 10, 0, 0);
                Cita nuevaCita9 = new Cita(3, 2, fecha7, "Pendiente", "Regular");
                Cita citaAgendada8 = citaDAO.agendarCita(nuevaCita9);

                if (citaAgendada8 != null) {
                    System.out.println("Cita agendada con éxito: " + citaAgendada8.getIdCita());
                } else {
                    System.out.println("Error al agendar la cita.");
                }
            } else {
                System.out.println("Error al agendar la primera cita.");
            }
        } catch (PersistenciaException e) {
            System.err.println("Error de persistencia al agendar citas: " + e.getMessage());
            e.printStackTrace();
        }

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
            Paciente paciente1 = pacienteDAO.consultarPacientePorID(17);
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
        
        // 1. Agendar una cita
        try {
            LocalDateTime fecha3 = LocalDateTime.of(2025, 2, 28, 10, 0, 0);
            Cita nuevaCita = new Cita(3, 2, fecha3, "Pendiente", "Regular");
            Cita citaAgendada4 = citaDAO.agendarCita(nuevaCita);
            if (citaAgendada4 != null) {
                System.out.println("Cita agendada con éxito: " + citaAgendada4.getIdCita());
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

        // 2. Consultar una cita por ID
        Cita citaConsultada3 = citaDAO.consultarCitaPorID(1);
        if (citaConsultada != null) {
            System.out.println("ID Paciente: " + citaConsultada3.getIdPaciente());
            System.out.println("ID Medico: " + citaConsultada3.getIdMedico());
            System.out.println("Fecha y Hora: " + citaConsultada3.getFecha());
            System.out.println("Estado: " + citaConsultada3.getEstadoCita());
            System.out.println("Tipo de Cita: " + citaConsultada3.getTipoCita());
        } else {
            System.out.println("Cita no encontrada.");
        }

        // 3. Cancelar una cita
        boolean citaCancelada2 = citaDAO.cancelarCita(9);
        System.out.println("Cita cancelada: " + citaCancelada2);
        
        //Cita emergencia
        try {
            int idPaciente = 4;

            boolean citaEmergenciaAgendada = citaDAO.agendarCitaEmergencia(idPaciente);

            if (citaEmergenciaAgendada) {
                System.out.println("Cita de emergencia agendada con éxito.");

            } else {
                System.out.println("Error al agendar la cita.");
            }
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
        
        // 2. Consultar una cita por ID
        Cita citaConsultada2 = citaDAO.consultarCitaPorID(1);
        if (citaConsultada != null) {
            System.out.println("ID Paciente: " + citaConsultada2.getIdPaciente());
            System.out.println("ID Medico: " + citaConsultada2.getIdMedico());
            System.out.println("Fecha y Hora: " + citaConsultada2.getFecha());
            System.out.println("Estado: " + citaConsultada2.getEstadoCita());
            System.out.println("Tipo de Cita: " + citaConsultada2.getTipoCita());
        } else {
            System.out.println("Cita no encontrada.");
        }

        // 3. Cancelar una cita
        boolean citaCancelada3 = citaDAO.cancelarCita(9);
        System.out.println("Cita cancelada: " + citaCancelada3);
        
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
        
        // Datos de prueba para agregar una consulta
        int idCita = 1; // Reemplaza con un ID de cita válido
        String tipoConsulta = "Consulta General";
        String fechaString = "2024-03-15";
        String diagnostico = "Resfriado común";
        String notas = "Descanso y medicamentos";;
        int idConsultaAEliminar = 1; // Reemplaza con un ID de consulta existente
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse(fechaString);
        Date fecha2 = new Date(utilDate.getTime());
        
//        // Test agregarConsulta
//        Consulta consulta = new Consulta(idCita, tipoConsulta, fecha2, diagnostico, notas);
//        boolean consultaAgregada = consultaDAO.agregarConsulta(consulta.getIdCita(), consulta.getTipoConsulta(), consulta.getFecha(), consulta.getDiagnosticoConsulta(), consulta.getNotasConsulta());
//        if (consultaAgregada) {
//            System.out.println("Consulta agregada correctamente.");
//        } else {
//            System.out.println("No se pudo agregar la consulta.");
//        }
//        
//        // Test eliminarConsulta
//        boolean consultaEliminada = consultaDAO.eliminarConsulta(idConsultaAEliminar);
//        if (consultaEliminada) {
//                System.out.println("Consulta eliminada correctamente.");
//        } else {
//            System.out.println("No se pudo eliminar la consulta.");
//        }
    }
}