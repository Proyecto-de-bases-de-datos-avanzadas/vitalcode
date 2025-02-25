package entidades;

import java.sql.Date;

/**
 *
 * @author erika
 */
public class Consulta {
    private int idConsulta;
    private int idCita;
    private String tipoConsulta; 
    private Date fecha;
    private String diagnosticoConsulta;
    private String notasConsulta; 

    public Consulta(int idCita, String tipoConsulta, Date fecha) {
        this.idCita = idCita;
        this.tipoConsulta = tipoConsulta;
        this.fecha = fecha;
    }

    public Consulta(int idCita, String tipoConsulta, Date fecha, String diagnosticoConsulta) {
        this.idCita = idCita;
        this.tipoConsulta = tipoConsulta;
        this.fecha = fecha;
        this.diagnosticoConsulta = diagnosticoConsulta;
    }

    public Consulta(int idCita, String tipoConsulta, Date fecha, String diagnosticoConsulta, String notasConsulta) {
        this.idCita = idCita;
        this.tipoConsulta = tipoConsulta;
        this.fecha = fecha;
        this.diagnosticoConsulta = diagnosticoConsulta;
        this.notasConsulta = notasConsulta;
    }

    public Consulta(int idConsulta, int idCita, String tipoConsulta, Date fecha, String diagnosticoConsulta, String notasConsulta) {
        this.idConsulta = idConsulta;
        this.idCita = idCita;
        this.tipoConsulta = tipoConsulta;
        this.fecha = fecha;
        this.diagnosticoConsulta = diagnosticoConsulta;
        this.notasConsulta = notasConsulta;
    }

    public Consulta() {
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDiagnosticoConsulta() {
        return diagnosticoConsulta;
    }

    public void setDiagnosticoConsulta(String diagnosticoConsulta) {
        this.diagnosticoConsulta = diagnosticoConsulta;
    }

    public String getNotasConsulta() {
        return notasConsulta;
    }

    public void setNotasConsulta(String notasConsulta) {
        this.notasConsulta = notasConsulta;
    }
}