/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package GUI;

import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import configuracion.DependencyInjector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erika
 */
public class inicio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       frmIniciarSesion inicioSesion = new frmIniciarSesion();
        inicioSesion.setVisible(true);
       
           
    }
    
}
