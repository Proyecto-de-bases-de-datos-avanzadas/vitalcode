package entidades;

import java.sql.Date;

/**
 *
 * @author erika
 */
public class Paciente {
    
    private int idUsuario ;
    private String correoElectronicoPaciente;
    private String nombrePaciente;
    private String apellidoPaterno;
    private String apellidoMateno;
    private String telefono;
    private Date fechaNacPaciente; 

    public Paciente(String correoElectronicoPaciente, String nombrePaciente, String apellidoPaterno, String apellidoMateno, String telefono, Date fechaNacPaciente) {
        this.correoElectronicoPaciente = correoElectronicoPaciente;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMateno = apellidoMateno;
        this.telefono = telefono;
        this.fechaNacPaciente = fechaNacPaciente;
    }

    public Paciente() {
    }

    public Paciente(int idUsuario, String correoElectronicoPaciente, String nombrePaciente, String apellidoPaterno, String apellidoMateno, String telefono, Date fechaNacPaciente) {
        this.idUsuario = idUsuario;
        this.correoElectronicoPaciente = correoElectronicoPaciente;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMateno = apellidoMateno;
        this.telefono = telefono;
        this.fechaNacPaciente = fechaNacPaciente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreoElectronicoPaciente() {
        return correoElectronicoPaciente;
    }

    public void setCorreoElectronicoPaciente(String correoElectronicoPaciente) {
        this.correoElectronicoPaciente = correoElectronicoPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMateno() {
        return apellidoMateno;
    }

    public void setApellidoMateno(String apellidoMateno) {
        this.apellidoMateno = apellidoMateno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacPaciente() {
        return fechaNacPaciente;
    }

    public void setFechaNacPaciente(Date fechaNacPaciente) {
        this.fechaNacPaciente = fechaNacPaciente;
    }
}