/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

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
        PacienteNDTO pacienteAregistrar = new PacienteNDTO("loquesea@gmail.com", "Alex", "Nieblas", "Moreno", "6442253326", Date.valueOf("2005-08-12"));
        UsuarioNDTO usuarioARegistrar = new UsuarioNDTO("LOQUE", "otra", "Paciente");
        try{
            DependencyInjector.crearPacienteBO().registrarPaciente(usuarioARegistrar, pacienteAregistrar);
        }catch(NegocioException ne){
            ne.printStackTrace();
        }
    }
}
