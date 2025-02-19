/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Exception.PersistenciaException;
import java.sql.Date;

/**
 *
 * @author JR
 */
public interface IConsultaDAO {
    public boolean agregarConsulta(int idCita, String tipoDeConsulta, Date fechaHora, String diagnostico, String notas) throws PersistenciaException;
    
    public boolean eliminarConsulta(int idConsulta) throws PersistenciaException;
}
