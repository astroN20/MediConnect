package com.medconnect.service;

import com.medconnect.model.Medico;
import com.medconnect.repository.IMedicoRepository;

import java.util.List;

public class MedicoService {
    private final IMedicoRepository repo;

    public MedicoService(IMedicoRepository repo) {
        this.repo = repo;
    }

    public void guardarMedico(Medico m) throws Exception {
        repo.save(m);
    }

    public List<Medico> listarMedicos() throws Exception {
        return repo.findAll();
    }
}
