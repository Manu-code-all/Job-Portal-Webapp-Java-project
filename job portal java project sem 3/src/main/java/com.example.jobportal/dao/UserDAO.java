package com.example.jobportal.dao;

import com.example.jobportal.model.User;
import com.example.jobportal.util.DBConnectionPool;

import java.sql.*;

public class UserDAO {

    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            int rows = ps.executeUpdate();
            if (rows == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) user.setId(rs.getInt(1));
            }
            return true;
        }
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, email, password_hash, role, created_at FROM users WHERE email=?";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    u.setRole(rs.getString("role"));
                    // created_at mapping skipped
                    return u;
                }
            }
        }
        return null;
    }
}
