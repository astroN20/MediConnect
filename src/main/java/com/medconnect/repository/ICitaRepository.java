package com.medconnect.repository;

import com.medconnect.model.ICita;
import com.medconnect.model.Medico;
import java.sql.Date;
import java.util.List;

public interface ICitaRepository {
    void save(ICita cita) throws Exception;
    List<ICita> findByMedicoAndFecha(Medico medico, Date fecha) throws Exception;
}
