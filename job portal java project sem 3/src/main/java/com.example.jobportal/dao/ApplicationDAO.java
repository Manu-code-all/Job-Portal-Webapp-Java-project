package com.example.jobportal.dao;

import com.example.jobportal.model.Application;
import com.example.jobportal.util.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    // APPLY TO JOB
    public boolean apply(Application app) throws SQLException {
        String sql = "INSERT INTO applications (job_id, seeker_id, resume_path, cover_letter) VALUES (?,?,?,?)";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, app.getJobId());
            ps.setInt(2, app.getSeekerId());
            ps.setString(3, app.getResumePath());
            ps.setString(4, app.getCoverLetter());

            return ps.executeUpdate() > 0;
        }
    }

    // LIST APPLICATIONS BY JOB (Employer)
    public List<Application> listByJob(int jobId) throws SQLException {
        String sql = "SELECT * FROM applications WHERE job_id=?";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            List<Application> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    // LIST APPLICATIONS BY JOB SEEKER
    public List<Application> listBySeeker(int seekerId) throws SQLException {
        String sql = "SELECT * FROM applications WHERE seeker_id=?";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seekerId);
            ResultSet rs = ps.executeQuery();

            List<Application> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    // UPDATE APPLICATION STATUS (Employer)
    public boolean updateStatus(int applicationId, String status) throws SQLException
