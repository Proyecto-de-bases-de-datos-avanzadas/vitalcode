/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import BO.DireccionBO;
import BO.MedicoBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import DTO.CitaDTO;
import DTO.DireccionNDTO;
import DTO.HorarioDTO;
import DTO.MedicoDTO;
import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import configuracion.DependencyInjector;
import java.sql.Date;
import Exception.NegocioException;
import Exception.PersistenciaException;
import conexion.ConexionBD;
import conexion.IConexionBD;
import entidades.Horario;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author alexnieblas
 */
public class Negocio {

    public static void main(String[] args) throws PersistenciaException, NegocioException {
        IConexionBD conexionBD = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexionBD);
        // Crear una instancia de MedicoBO
        //faltan inserts en la tabla jsj
        MedicoBO medicoBO = new MedicoBO(conexionBD);
try {
            // Llamar al método recuperarMedicoPorID
            MedicoDTO medicoDTO = medicoBO.recuperarMedicoID(5); // Cambia el ID por el que deseas probar
            if (medicoDTO != null) {
                // Imprimir los detalles del médico recuperado
                System.out.println("ID Usuario: " + medicoDTO.getId());
                System.out.println("Nombre: " + medicoDTO.getNombre());
                System.out.println("Especialidad: " + medicoDTO.getEspecialidad());
                System.out.println("Cedula: " + medicoDTO.getCedula());
                System.out.println("Estado: " + medicoDTO.getEstado());
            } else {
                System.out.println("No se encontró ningún médico con ese ID.");
            }
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
        // Consultar todas las citas de un paciente
            try {
                List<CitaDTO> todasLasCitas = pacienteBO.obtenerTodasLasCitas(4);
                System.out.println("Todas las citas del paciente:");
                for (CitaDTO citaDTO : todasLasCitas) {
                    System.out.println(citaDTO.getFecha());
                }
            } catch (SQLException e) {
                System.err.println("Error al consultar todas las citas: " + e.getMessage());
            }

            // Consultar citas pendientes de un paciente
            try {
                List<CitaDTO> citasPendientes = pacienteBO.obtenerCitasPendientes(4);
                System.out.println("Citas pendientes del paciente:");
                for (CitaDTO citaDTO : citasPendientes) {
                    System.out.println(citaDTO.getFecha());
                }
            } catch (SQLException e) {
                System.err.println("Error al consultar las citas pendientes: " + e.getMessage());
            }

        
        

        //Actualizar paciente 
        PacienteNDTO pacienteActualizar = new PacienteNDTO(18, "CorreoNegocio11@gmail.com", "Negocio", "Capa", "Negpcio", "852085720", Date.valueOf("2005-05-20"));
        try {
            // Llamar al método actualizarPaciente
            boolean exito = pacienteBO.ActualizarPaciente(pacienteActualizar);
            if (exito) {
                System.out.println("Paciente actualizado exitosamente.");
            } else {
                System.out.println("Error al actualizar el paciente.");
            }
        } catch (NegocioException | PersistenciaException e) {
            e.printStackTrace();
        }

        System.out.println("prueba baja medico");
        
        boolean baja = medicoBO.BajaMedico(77);
        if (baja) {
            System.out.println("medico dado de baja");
        }else{
            System.out.println("no se pudo");
        }
        System.out.println("prueba alta medico");
        boolean alta = medicoBO.AltaMedico(77);
        if (alta) {
            System.out.println("medico dado de alta");
        }else{
            System.out.println("no se pudo x2");
        }
       //recuperar medico por nombre de usuario
        String usuarioMedido = "23623gf23";
        MedicoDTO medicoPorUsuario = medicoBO.recuperarMedicoUsuario(usuarioMedido);
        System.out.println("nombre medico: "+medicoPorUsuario.getNombre());
        
        // Llamar al método recuperarHorarioMedico
        List<HorarioDTO> horarios = medicoBO.consultarHorarioMedico(3); // Cambia el ID por el que deseas probar
        if (!horarios.isEmpty()) {
            // Imprimir los detalles del horario recuperado
            for (HorarioDTO horario : horarios) {
                System.out.println("ID Horario: " + horario.getId());
                System.out.println("Día de la Semana: " + horario.getDiaSemana());
                System.out.println("Hora de Entrada: " + horario.getHoraEntrada());
                System.out.println("Hora de Salida: " + horario.getHoraSalida());
                System.out.println("-----------------------------------");
            }
        } else {
            System.out.println("No se encontró ningún horario para el médico con ese ID.");
        }
        
         
        
        
        //recuperarar direccion por id
        
        int idPaciente = 17;
        DireccionBO direccionBO = new DireccionBO(conexionBD);
        DireccionNDTO direccion = direccionBO.recuperarDireccionPorID(idPaciente);
        System.out.println("Direccion Recuperada");
        System.out.println("Calle: "+direccion.getCalle());
        
        
        PacienteNDTO paciente = pacienteBO.recuperarPacienteID(idPaciente);
        System.out.println("Paciente encontrado");
        System.out.println("Nombre Pacienre"+ paciente.getNombre());
        
        
        
        String nombreUsuario = "lucia123";
        
        UsuarioBO usuarioBO = new UsuarioBO(conexionBD);
        try {
            UsuarioNDTO usuario = usuarioBO.recuperarUsuarioPorNombre(nombreUsuario);
            if (usuario != null) {
                System.out.println("Usuario encontrado:");
                System.out.println("id :"+usuario.getId());
                System.out.println("Nombre de Usuario: " + usuario.getNombre_usuario());
                System.out.println("Contraseña: " + usuario.getContraseniaUsuario());
                System.out.println("Tipo de Usuario: " + usuario.getTipo_usuario());
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (NegocioException | PersistenciaException e) {
            e.printStackTrace();
        }
    
        
        PacienteNDTO pacienteAregistrar = new PacienteNDTO("odiolosDAO@gmail.com", "Lucia", "Vasquez", "Gastelum", "6442546583", Date.valueOf("2005-07-05"));
        UsuarioNDTO usuarioARegistrar = new UsuarioNDTO("lucia123", "miisis", "Paciente");

        DireccionNDTO direccionq = new DireccionNDTO(pacienteAregistrar.getIdUsuario(), "calle11", "1352", "colonia1");
        try{
            DependencyInjector.crearPacienteBO().registrarPaciente(usuarioARegistrar, pacienteAregistrar,direccion);
        
        } catch (NegocioException ne) {
            System.err.println("Error de negocio: " + ne.getMessage());
            ne.printStackTrace();
        } catch (PersistenciaException pe) {
            System.err.println("Error de persistencia: " + pe.getMessage());
            pe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
