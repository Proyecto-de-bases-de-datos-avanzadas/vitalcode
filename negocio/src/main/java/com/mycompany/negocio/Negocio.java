/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import DTO.DireccionNDTO;
import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import configuracion.DependencyInjector;
import java.sql.Date;
import Exception.NegocioException;
import Exception.PersistenciaException;
/**
 *
 * @author alexnieblas
 */
public class Negocio {

    public static void main(String[] args) throws PersistenciaException {
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
