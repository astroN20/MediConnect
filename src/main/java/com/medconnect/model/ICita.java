package com.medconnect.model;

import java.sql.Date;
import java.sql.Time;

public interface ICita {
    Integer getId();
    Paciente getPaciente();
    Medico getMedico();
    Date getFecha();
    Time getHora();
    String getTipo();
    String getObservaciones();
}
