/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

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

    public static void main(String[] args) throws PersistenciaException {
        try {
            IConexionBD conexionBD = new ConexionBD();
            Connection conexion = conexionBD.crearConexion();

            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre_usuario("JuanPGar56");
            nuevoUsuario.setContraseniaUsuario("contraseñaSegura");
            nuevoUsuario.setTipo_usuario("Paciente");

            Usuario usuarioAgregado = usuarioDAO.agregarUsuario(nuevoUsuario);

            if (usuarioAgregado != null) {
                System.out.println("Usuario agregado con éxito. ID del usuario: " + usuarioAgregado.getIdUsuario());

                int idUsuario = usuarioAgregado.getIdUsuario();

                Date fechaNacimientoSql = Date.valueOf("1990-05-15");

                Paciente nuevoPaciente = new Paciente();
                nuevoPaciente.setIdUsuario(idUsuario);
                nuevoPaciente.setCorreoElectronicoPaciente("JuanPGar56@gmail.com");
                nuevoPaciente.setNombrePaciente("Juan");
                nuevoPaciente.setApellidoPaterno("Pérez");
                nuevoPaciente.setApellidoMateno("García");
                nuevoPaciente.setTelefono("5551234567");
                nuevoPaciente.setFechaNacPaciente(fechaNacimientoSql);

                boolean pacienteAgregado = pacienteDAO.agregarPaciente(nuevoPaciente);

                if (pacienteAgregado) {
                    System.out.println("Paciente agregado con éxito.");
                } else {
                    System.out.println("Error al agregar el paciente.");
                }
            } else {
                System.out.println("Error al agregar el usuario.");
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
