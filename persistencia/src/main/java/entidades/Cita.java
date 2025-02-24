/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;

/**
 *
 * @author erika
 */
public class Cita {
    private int idCita;
    private int idPaciente;
    private int idMedico;

    private LocalDateTime fecha;
    private String estadoCita;
    private int folioCita;
    private String tipoCita;

    public Cita() {
    }

    public Cita(int idCita, int idPaciente, int idMedico, LocalDateTime fecha, String estadoCita, String tipoCita) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.estadoCita = estadoCita;
        this.tipoCita = tipoCita;
    }

    

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public int getFolioCita() {
        return folioCita;
    }

    public void setFolioCita(int folioCita) {
        this.folioCita = folioCita;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }
    
    

    public Cita(int idCita, int idPaciente, int idMedico, LocalDateTime fecha, String estadoCita, int folioCita, String tipoCita) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;

        this.fecha = fecha;
        this.estadoCita = estadoCita;
        this.folioCita = folioCita;
        this.tipoCita = tipoCita;
    }

    public Cita(int idPaciente, int idMedico, LocalDateTime fecha, String estadoCita, String tipoCita) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.estadoCita = estadoCita;
        this.tipoCita = tipoCita;
    }

}  

