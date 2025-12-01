package com.medconnect.repository;

import com.medconnect.db.DBConnection;
import com.medconnect.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositoryJDBC implements IPacienteRepository {
    @Override
    public void save(Paciente p) throws Exception {
        try (Connection c = DBConnection.getConnection()) {
            if (p.getId() == null) {
                String sql = "INSERT INTO paciente(nombre, documento, telefono) VALUES (?, ?, ?)";
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getDocumento());
                ps.setString(3, p.getTelefono());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) p.setId(rs.getInt(1));
            } else {
                String sql = "UPDATE paciente SET nombre=?, documento=?, telefono=? WHERE id=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getDocumento());
                ps.setString(3, p.getTelefono());
                ps.setInt(4, p.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public List<Paciente> findAll() throws Exception {
        List<Paciente> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT id,nombre,documento,telefono FROM paciente";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Paciente(rs.getInt("id"), rs.getString("nombre"), rs.getString("documento"), rs.getString("telefono")));
            }
        }
        return list;
    }

    @Override
    public Paciente findById(int id) throws Exception {
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT id,nombre,documento,telefono FROM paciente WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Paciente(rs.getInt("id"), rs.getString("nombre"), rs.getString("documento"), rs.getString("telefono"));
            }
        }
        return null;
    }
}
