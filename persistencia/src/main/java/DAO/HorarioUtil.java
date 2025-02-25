/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author erika
 */
import entidades.Horario;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HorarioUtil {
    public static Map<String, List<Time>> generarIntervalosPorDia(List<Horario> horarios) {
        Map<String, List<Time>> intervalosPorDia = new HashMap<>();
        
        for (Horario horario : horarios) {
            List<Time> intervalos = new ArrayList<>();
            long intervalMillis = TimeUnit.MINUTES.toMillis(30);
            long entradaMillis = horario.getHoraEntrada().getTime();
            long salidaMillis = horario.getHoraSalida().getTime();

            for (long currentTime = entradaMillis; currentTime < salidaMillis; currentTime += intervalMillis) {
                intervalos.add(new Time(currentTime));
            }

            intervalosPorDia.put(horario.getDiaSemana(), intervalos);
        }
        
        return intervalosPorDia;
    }
}

