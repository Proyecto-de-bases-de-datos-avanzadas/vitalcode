/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Time;

/**
 *
 * @author erika
 */
public class Horario {
    private int idHorario; 
    private String diaSemana; 
    private Time hora_Entrada;

    @Override
    public String toString() {
        return "Horario{" + "idHorario=" + idHorario + ", diaSemana=" + diaSemana + ", hora_Entrada=" + hora_Entrada + '}';
    }

    public Horario(int idHorario, String diaSemana, Time hora_Entrada) {
        this.idHorario = idHorario;
        this.diaSemana = diaSemana;
        this.hora_Entrada = hora_Entrada;
    }

    public Horario(String diaSemana, Time hora_Entrada) {
        this.diaSemana = diaSemana;
        this.hora_Entrada = hora_Entrada;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHora_Entrada() {
        return hora_Entrada;
    }

    public void setHora_Entrada(Time hora_Entrada) {
        this.hora_Entrada = hora_Entrada;
    }
    
}
