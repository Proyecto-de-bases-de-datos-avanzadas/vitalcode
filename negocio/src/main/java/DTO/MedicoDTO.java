package DTO;

/**
 *
 * @author erika
 */
public class MedicoDTO {
    private int id;
    private String nombre;
    private String especialidad;
    private String cedula;
    private String estado;

    public MedicoDTO(String nombre, String especialidad, String cedula, String estado) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.estado = estado;
    }

    public MedicoDTO() {
    }

    public MedicoDTO(int id, String nombre, String especialidad, String cedula, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}