/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author erika
 */
public class Direccion {
    private int id_Paciente;
    private String calle;
    private String numero; 
    private String colonia; 

    public Direccion(int id_Paciente, String calle, String numero, String colonia) {
        this.id_Paciente = id_Paciente;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
    }

    public Direccion() {
    }

    public int getId_Paciente() {
        return id_Paciente;
    }

    public void setId_Paciente(int id_Paciente) {
        this.id_Paciente = id_Paciente;
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

