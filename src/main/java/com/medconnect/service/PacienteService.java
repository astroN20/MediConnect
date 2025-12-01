package com.medconnect.service;

import com.medconnect.model.Paciente;
import com.medconnect.repository.IPacienteRepository;

import java.util.List;

public class PacienteService {
    private final IPacienteRepository repo;

    public PacienteService(IPacienteRepository repo) {
        this.repo = repo;
    }

    public void guardarPaciente(Paciente p) throws Exception {
        repo.save(p);
    }

    public List<Paciente> listarPacientes() throws Exception {
        return repo.findAll();
    }
}
