package com.medconnect.model;

public class Medico {
    private Integer id;
    private String nombre;
    private String especialidad;

    public Medico() {}
    public Medico(Integer id, String nombre, String especialidad) {
        this.id = id; this.nombre = nombre; this.especialidad = especialidad;
    }
    public Medico(String nombre, String especialidad) {
        this(null, nombre, especialidad);
    }
    // getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    @Override
    public String toString() { return id + " - " + nombre + " (" + especialidad + ")"; }
}
