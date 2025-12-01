package com.medconnect.repository;

import com.medconnect.model.Paciente;
import java.util.List;

public interface IPacienteRepository {
    void save(Paciente p) throws Exception;
    List<Paciente> findAll() throws Exception;
    Paciente findById(int id) throws Exception;
}
