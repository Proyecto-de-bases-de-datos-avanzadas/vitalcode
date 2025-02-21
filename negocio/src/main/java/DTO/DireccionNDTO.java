/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author erika
 */
public class DireccionNDTO {
    private int idPaciente;
    private String calle;
    private String numero;
    private String colonia;

    public DireccionNDTO() {
    }

    public DireccionNDTO(int idPaciente, String calle, String numero, String colonia) {
        this.idPaciente = idPaciente;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
    
    
}
