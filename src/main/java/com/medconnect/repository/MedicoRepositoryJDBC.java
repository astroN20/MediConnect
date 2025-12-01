package com.medconnect.repository;

import com.medconnect.db.DBConnection;
import com.medconnect.model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepositoryJDBC implements IMedicoRepository {
    @Override
    public void save(Medico m) throws Exception {
        try (Connection c = DBConnection.getConnection()) {
            if (m.getId() == null) {
                String sql = "INSERT INTO medico(nombre, especialidad) VALUES (?, ?)";
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, m.getNombre());
                ps.setString(2, m.getEspecialidad());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) m.setId(rs.getInt(1));
            } else {
                String sql = "UPDATE medico SET nombre=?, especialidad=? WHERE id=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, m.getNombre());
                ps.setString(2, m.getEspecialidad());
                ps.setInt(3, m.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public List<Medico> findAll() throws Exception {
        List<Medico> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT id,nombre,especialidad FROM medico";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Medico(rs.getInt("id"), rs.getString("nombre"), rs.getString("especialidad")));
            }
        }
        return list;
    }

    @Override
    public Medico findById(int id) throws Exception {
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT id,nombre,especialidad FROM medico WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medico(rs.getInt("id"), rs.getString("nombre"), rs.getString("especialidad"));
            }
        }
        return null;
    }
}
