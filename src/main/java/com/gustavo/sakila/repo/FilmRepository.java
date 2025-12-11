package com.gustavo.sakila.repo;

import com.gustavo.sakila.db.DB;
import com.gustavo.sakila.model.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmRepository extends CrudRepository<Film, Integer> {

    public FilmRepository() {
        super(DB.ds());
    }

    public Optional<Film> findById(int id) throws SQLException {
        String sql = "SELECT film_id, title, language_id, last_update FROM film WHERE film_id=?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Film(
                            rs.getInt("film_id"),
                            rs.getString("title"),
                            (Integer) rs.getObject("language_id"),
                            rs.getTimestamp("last_update").toInstant()
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public List<Film> firstN(int n) throws SQLException {
        String sql = "SELECT film_id, title, language_id, last_update FROM film ORDER BY film_id LIMIT ?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, n);
            try (ResultSet rs = ps.executeQuery()) {
                List<Film> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(new Film(
                            rs.getInt("film_id"),
                            rs.getString("title"),
                            (Integer) rs.getObject("language_id"),
                            rs.getTimestamp("last_update").toInstant()
                    ));
                }
                return out;
            }
        }
    }

    public long count() throws SQLException {
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM film");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }
}