/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import BO.DireccionBO;
import BO.MedicoBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import DTO.DireccionNDTO;
import DTO.MedicoDTO;
import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import configuracion.DependencyInjector;
import java.sql.Date;
import Exception.NegocioException;
import Exception.PersistenciaException;
import conexion.ConexionBD;
import conexion.IConexionBD;
/**
 *
 * @author alexnieblas
 */
public class Negocio {

    public static void main(String[] args) throws PersistenciaException, NegocioException {
        IConexionBD conexionBD = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexionBD);
        // Crear una instancia de MedicoBO
        MedicoBO medicoBO = new MedicoBO(conexionBD);
         try {
            // Llamar al método recuperarMedicoPorID
            MedicoDTO medicoDTO = medicoBO.recuperarMedicoID(3); // Cambia el ID por el que deseas probar
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
        
        //Actualizar paciente 
        PacienteNDTO pacienteActualizar = new PacienteNDTO(18, "CorreoNegocio@gmail.com", "Negocio", "Capa", "Negpcio", "852085720", Date.valueOf("2005-05-20"));
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
