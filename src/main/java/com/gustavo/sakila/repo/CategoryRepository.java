package com.gustavo.sakila.repo;

import com.gustavo.sakila.db.DB;
import com.gustavo.sakila.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository extends CrudRepository<Category, Integer> {

    public CategoryRepository() {
        super(DB.ds());
    }

    public List<Category> findAll() throws SQLException {
        String sql = "SELECT category_id, name, last_update FROM category ORDER BY category_id";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Category> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getTimestamp("last_update").toInstant()
                ));
            }
            return out;
        }
    }

    public Optional<Category> findById(int id) throws SQLException {
        String sql = "SELECT category_id, name, last_update FROM category WHERE category_id=?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Category(
                            rs.getInt("category_id"),
                            rs.getString("name"),
                            rs.getTimestamp("last_update").toInstant()
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public int insert(String name) throws SQLException {
        String sql = "INSERT INTO category(name) VALUES (?)";
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
        String sql = "UPDATE category SET name=? WHERE category_id=?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id=?";
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public long count() throws SQLException {
        try (Connection c = con(); PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM category");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }
}