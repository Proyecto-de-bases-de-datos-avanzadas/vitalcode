package DTO;

import java.sql.Time;

/**
 *
 * @author erika
 */
public class HorarioDTO {
     private int id;
    private String diaSemana;
    private Time horaEntrada;
    private Time horaSalida;

    public HorarioDTO() {
    }

    public HorarioDTO(String diaSemana, Time horaEntrada, Time horaSalida) {
        this.diaSemana = diaSemana;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public HorarioDTO(int id, String diaSemana, Time horaEntrada, Time horaSalida) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }
}