package com.medconnect.service;

import com.medconnect.model.ICita;
import com.medconnect.model.Medico;
import com.medconnect.repository.ICitaRepository;

import java.sql.Date;
import java.util.List;

public class CitaService {
    private final ICitaRepository repo;

    public CitaService(ICitaRepository repo) {
        this.repo = repo;
    }

    public void registrarCita(ICita cita) throws Exception {
        repo.save(cita);
    }

    public List<ICita> listarCitasPorMedicoYFecha(Medico m, Date fecha) throws Exception {
        return repo.findByMedicoAndFecha(m, fecha);
    }
}
