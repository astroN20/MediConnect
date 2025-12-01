package com.medconnect.repository;

import com.medconnect.model.Medico;
import java.util.List;

public interface IMedicoRepository {
    void save(Medico m) throws Exception;
    List<Medico> findAll() throws Exception;
    Medico findById(int id) throws Exception;
}
