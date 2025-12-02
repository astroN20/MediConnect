package com.medconnect.repository;

import com.medconnect.db.DBConnection;
import com.medconnect.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaRepositoryJDBC implements ICitaRepository {
    private final IPacienteRepository pacienteRepo = new PacienteRepositoryJDBC();
  

    @Override
    public void save(ICita cita) throws Exception {
        // Inserta en tabla cita (asumiendo CitaPresencial)s
        try (Connection c = DBConnection.getConnection()) {
            String sql = "INSERT INTO cita(paciente_id, medico_id, fecha, hora, tipo, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cita.getPaciente().getId());
            ps.setInt(2, cita.getMedico().getId());
            ps.setDate(3, cita.getFecha());
            ps.setTime(4, cita.getHora());
            ps.setString(5, cita.getTipo());
            ps.setString(6, cita.getObservaciones());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            // Esto captura conflicto de horario por la UNIQUE KEY definida en tabla
            throw new Exception("Ya existe una cita para ese médico en la fecha y hora seleccionada.");
        }
    }

    @Override
    public List<ICita> findByMedicoAndFecha(Medico medico, Date fecha) throws Exception {
        List<ICita> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT id, paciente_id, medico_id, fecha, hora, tipo, observaciones FROM cita WHERE medico_id=? AND fecha=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, medico.getId());
            ps.setDate(2, fecha);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int pacienteId = rs.getInt("paciente_id");
                Paciente p = pacienteRepo.findById(pacienteId);
                ICita cita = new CitaPresencial(rs.getInt("id"), p, medico, rs.getDate("fecha"), rs.getTime("hora"), rs.getString("observaciones"));
                list.add(cita);
            }
        }
        return list;
    }
}
