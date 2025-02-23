/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author erika
 */


import java.util.List;

import java.util.List;

/**
 *
 * @author erika
 */
public class Medico {
    private int idUsuario;
    private String nombre;
    private String especialidadMedico;
    private String cedulaMedico;
    private String estadoMedico; 

    public Medico(int idUsuario, String nombre, String especialidadMedico, String cedulaMedico, String estadoMedico) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.especialidadMedico = especialidadMedico;
        this.cedulaMedico = cedulaMedico;
        this.estadoMedico = estadoMedico;
    }
    private List<Horario> horarioMedico; 

    public Medico(String nombre, String especialidadMedico, String cedulaMedico, String estadoMedico, List<Horario> horarioMedico) {
        this.nombre = nombre;
        this.especialidadMedico = especialidadMedico;
        this.cedulaMedico = cedulaMedico;
        this.estadoMedico = estadoMedico;
        this.horarioMedico = horarioMedico;
    }

    public Medico() {
    }

    public Medico(int idUsuario, String nombre, String especialidadMedico, String cedulaMedico, String estadoMedico, List<Horario> horarioMedico) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.especialidadMedico = especialidadMedico;
        this.cedulaMedico = cedulaMedico;
        this.estadoMedico = estadoMedico;
        this.horarioMedico = horarioMedico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidadMedico() {
        return especialidadMedico;
    }

    public void setEspecialidadMedico(String especialidadMedico) {
        this.especialidadMedico = especialidadMedico;
    }

    public String getCedulaMedico() {
        return cedulaMedico;
    }

    public void setCedulaMedico(String cedulaMedico) {
        this.cedulaMedico = cedulaMedico;
    }

    public String getEstadoMedico() {
        return estadoMedico;
    }

    public void setEstadoMedico(String estadoMedico) {
        this.estadoMedico = estadoMedico;
    }

    public List<Horario> getHorarioMedico() {
        return horarioMedico;
    }

    public void setHorarioMedico(List<Horario> horarioMedico) {
        this.horarioMedico = horarioMedico;
    }
}
