package com.medconnect.model;


public class Paciente {
    private Integer id;
    private String nombre;
    private String documento;
    private String telefono;

    public Paciente() {}
    public Paciente(Integer id, String nombre, String documento, String telefono) {
        this.id = id; this.nombre = nombre; this.documento = documento; this.telefono = telefono;
    }
    public Paciente(String nombre, String documento, String telefono) {
        this(null, nombre, documento, telefono);
    }
    // getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    @Override
    public String toString() { return id + " - " + nombre; }
}