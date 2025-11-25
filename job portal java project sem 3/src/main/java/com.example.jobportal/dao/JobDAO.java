package com.example.jobportal.dao;

import com.example.jobportal.model.Job;
import com.example.jobportal.util.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    // CREATE JOB (Employer)
    public int createJob(Job job) throws SQLException {
        String sql = "INSERT INTO jobs (employer_id, title, description, requirements, salary, location, status) " +
                     "VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, job.getEmployerId());
            ps.setString(2, job.getTitle());
            ps.setString(3, job.getDescription());
            ps.setString(4, job.getRequirements());
            ps.setString(5, job.getSalary());
            ps.setString(6, job.getLocation());
            ps.setString(7, "PENDING");

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    // LIST ALL JOBS (Admin view)
    public List<Job> listAllJobs() throws SQLException {
        String sql = "SELECT * FROM jobs";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Job> jobs = new ArrayList<>();
            while (rs.next()) {
                jobs.add(mapRowToJob(rs));
            }
            return jobs;
        }
    }

    // LIST JOBS BY EMPLOYER (Employer dashboard)
    public List<Job> listJobsByEmployer(int employerId) throws SQLException {
        String sql = "SELECT * FROM jobs WHERE employer_id=?";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            List<Job> jobs = new ArrayList<>();
            while (rs.next()) {
                jobs.add(mapRowToJob(rs));
            }
            return jobs;
        }
    }

    // DELETE JOB (Employer)
    public boolean deleteJob(int jobId, int employerId) throws SQLException {
        String sql = "DELETE FROM jobs WHERE id=? AND employer_id=?";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, employerId);

            return ps.executeUpdate() > 0;
        }
    }

    // APPROVE JOB (Admin) — TRANSACTION MANAGEMENT
    public boolean approveJob(int jobId, int adminId) throws SQLException {

        String updateSql = "UPDATE jobs SET status='APPROVED' WHERE id=?";
        String auditSql = "INSERT INTO job_audit (job_id, action, performed_by, performed_at) VALUES (?,?,?,NOW())";

        Connection conn = null;
        try {
            conn = DBConnectionPool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, jobId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(auditSql)) {
                ps.setInt(1, jobId);
                ps.setString(2, "APPROVED");
                ps.setInt(3, adminId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // REJECT JOB (Admin)
    public boolean rejectJob(int jobId, int adminId) throws SQLException {

        String updateSql = "UPDATE jobs SET status='REJECTED' WHERE id=?";
        String auditSql = "INSERT INTO job_audit (job_id, action, performed_by, performed_at) VALUES (?,?,?,NOW())";

        Connection conn = null;
        try {
            conn = DBConnectionPool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, jobId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(auditSql)) {
                ps.setInt(1, jobId);
                ps.setString(2, "REJECTED");
                ps.setInt(3, adminId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // LIST APPROVED JOBS (Job Seeker)
    public List<Job> listApprovedJobs() throws SQLException {
        String sql = "SELECT * FROM jobs WHERE status='APPROVED'";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Job> jobs = new ArrayList<>();
            while (rs.next()) {
                jobs.add(mapRowToJob(rs));
            }
            return jobs;
        }
    }

    // SEARCH JOBS (Job Seeker)
    public List<Job> searchJobs(String keyword) throws SQLException {
        String sql = "SELECT * FROM jobs WHERE status='APPROVED' AND title LIKE ?";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            List<Job> jobs = new ArrayList<>();
            while (rs.next()) {
                jobs.add(mapRowToJob(rs));
            }
            return jobs;
        }
    }

    // MAP ROW TO MODEL
    private Job mapRowToJob(ResultSet rs) throws SQLException {
        Job j = new Job();
        j.setId(rs.getInt("id"));
        j.setEmployerId(rs.getInt("employer_id"));
        j.setTitle(rs.getString("title"));
        j.setDescription(rs.getString("description"));
        j.setRequirements(rs.getString("requirements"));
        j.setSalary(rs.getString("salary"));
        j.setLocation(rs.getString("location"));
        j.setStatus(rs.getString("status"));
        return j;
    }
}
