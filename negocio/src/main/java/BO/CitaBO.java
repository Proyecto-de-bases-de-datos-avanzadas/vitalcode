/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.CitaDAO;

import DTO.CitaDTO;
import DTO.MedicoDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.CitaMapper;
import Mapper.MedicoMapper;
import conexion.IConexionBD;
import entidades.Cita;
import entidades.Medico;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erika
 */
public class CitaBO {
    private final CitaDAO citaDAO;

    public CitaBO(IConexionBD conexion){
        this.citaDAO = new CitaDAO(conexion);
    }

    public boolean citaDeEmergencia(int idPaciente) throws NegocioException {
        try {
            return citaDAO.agendarCitaEmergencia(idPaciente);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al agendar cita de emergencia para el paciente " + idPaciente, pe);
        }
    }

    public List<MedicoDTO> obtenerDoctoresDisponibles(String especialidad) throws NegocioException {
    try {
        List<Medico> medicos = citaDAO.obtenerDoctoresDisponibles(especialidad);
        List<MedicoDTO> medicosDTO = new ArrayList<>();
        for (Medico medico : medicos) {
            medicosDTO.add(MedicoMapper.toDTO(medico));
        }
        return medicosDTO;
    } catch (PersistenciaException pe) {
        throw new NegocioException("No se encontró doctores disponibles con la especialidad: " + especialidad, pe);
    }
}


    public void agregarCitaSimple(CitaDTO citaDTO) throws NegocioException {
        try {
            Cita cita = CitaMapper.toEntity(citaDTO);
            citaDAO.agregarCitaSimple(cita);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al agregar la cita: " + citaDTO, pe);
        }
    }

    public int validarDisponibilidad(int idMedico, LocalDateTime fecha) throws NegocioException {
        try {
            return citaDAO.validarDisponibilidad(idMedico, fecha);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al validar disponibilidad del médico con ID " + idMedico, pe);
        }
    }

    public CitaDTO agendarCita(CitaDTO citaDTO) throws NegocioException {
        try {
            Cita cita = CitaMapper.toEntity(citaDTO);
            cita = citaDAO.agendarCita(cita);
            return CitaMapper.toDto(cita);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al agendar la cita: " + citaDTO, pe);
        }
    }

    public boolean cancelarCita(int idCita) throws NegocioException {
        try {
            return citaDAO.cancelarCita(idCita);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al cancelar la cita con ID " + idCita, pe);
        }
    }

    public boolean existeCita(int idMedico, LocalDateTime fecha) throws NegocioException {
        try {
            return citaDAO.existeCita(idMedico, fecha);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al verificar la existencia de la cita para el médico con ID " + idMedico, pe);
        }
    }

    public List<String> obtenerHorarioDisponible(int idMedico) throws NegocioException {
        try {
            return citaDAO.obtenerHorarioDisponible(idMedico);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al obtener los horarios disponibles para el médico con ID " + idMedico, pe);
        }
    }

    public CitaDTO consultarCitaPorID(int idCita) throws NegocioException {
        try {
            Cita cita = citaDAO.consultarCitaPorID(idCita);
            return CitaMapper.toDto(cita);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error al consultar la cita con ID " + idCita, pe);
        }
    }
}


