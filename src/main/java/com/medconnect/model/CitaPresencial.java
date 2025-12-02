package com.medconnect.model;

import java.sql.Date;
import java.sql.Time;

public class CitaPresencial implements ICita {
    private Integer id;
    private Paciente paciente;
    private Medico medico;
    private Date fecha;
    private Time hora;
    private String tipo = "PRESENCIAL";
    private String observaciones;

    public CitaPresencial() {}
    public CitaPresencial(Paciente paciente, Medico medico, Date fecha, Time hora, String observaciones) {
        this(null, paciente, medico, fecha, hora, observaciones);
    }
    public CitaPresencial(Integer id, Paciente paciente, Medico medico, Date fecha, Time hora, String observaciones) {
        this.id = id; this.paciente = paciente; this.medico = medico; this.fecha = fecha; this.hora = hora; this.observaciones = observaciones;
    }
    // getters
    public Integer getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public Date getFecha() { return fecha; }
    public Time getHora() { return hora; }
    public String getTipo() { return tipo; }
    public String getObservaciones() { return observaciones; }
}
