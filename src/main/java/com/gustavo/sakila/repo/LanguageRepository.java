package com.gustavo.sakila.repo;

import com.gustavo.sakila.db.DB;
import com.gustavo.sakila.model.Language;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LanguageRepository extends CrudRepository<Language, Integer> {

    public LanguageRepository() {
        super(DB.ds());
    }

    public List<Language> findAll() throws SQLException {
        String sql = "SELECT language_id, name, last_update FROM language ORDER BY language_id";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Language> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new Language(
                        rs.getInt("language_id"),
                        rs.getString("name"),
                        rs.getTimestamp("last_update").toInstant()
                ));
            }
            return out;
        }
    }

    public Optional<Language> findById(int id) throws SQLException {
        String sql = "SELECT language_id, name, last_update FROM language WHERE language_id=?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Language(
                            rs.getInt("language_id"),
                            rs.getString("name"),
                            rs.getTimestamp("last_update").toInstant()
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public int insert(String name) throws SQLException {
        String sql = "INSERT INTO language(name) VALUES (?)";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            return -1;
        }
    }

    public boolean updateName(int id, String name) throws SQLException {
        String sql = "UPDATE language SET name=? WHERE language_id=?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM language WHERE language_id=?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public long count() throws SQLException {
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM language");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }
}