/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import BO.PacienteBO;
import BO.UsuarioBO;
import DTO.DireccionNDTO;
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
        int idPaciente = 17;
        PacienteBO pacienteBO = new PacienteBO(conexionBD);
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

        DireccionNDTO direccion = new DireccionNDTO(pacienteAregistrar.getIdUsuario(), "calle11", "1352", "colonia1");
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
