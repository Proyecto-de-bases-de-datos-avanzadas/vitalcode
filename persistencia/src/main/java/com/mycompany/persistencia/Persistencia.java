package com.mycompany.persistencia;

import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import Exception.PersistenciaException;
import conexion.ConexionBD;
import conexion.IConexionBD;
import entidades.Paciente;
import entidades.Usuario;
import java.sql.Connection;
import java.sql.Date;

/**
 *
 * @author alexnieblas
 */
public class Persistencia {

    public static void main(String[] args) {
        
        IConexionBD conexionBD = new ConexionBD();
        PacienteDAO pacienteDAO = new PacienteDAO(conexionBD);

        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre_usuario("LuisGF");
            nuevoUsuario.setContraseniaUsuario("contra");
            nuevoUsuario.setTipo_usuario("Paciente");

            Paciente nuevoPaciente = new Paciente();
            nuevoPaciente.setCorreoElectronicoPaciente("LuisGF@gmail.com");
            nuevoPaciente.setNombrePaciente("Luis");
            nuevoPaciente.setApellidoPaterno("Gonzalez");
            nuevoPaciente.setApellidoMateno("Fernandez");
            nuevoPaciente.setTelefono("5978645321");
            nuevoPaciente.setFechaNacPaciente(Date.valueOf("1995-01-20"));

            boolean exito = pacienteDAO.agregarUsuarioYPaciente(nuevoUsuario, nuevoPaciente);

            if (exito) {
                System.out.println("Usuario y paciente agregados con éxito.");
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
    }
}