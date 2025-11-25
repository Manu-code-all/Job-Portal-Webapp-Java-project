package com.example.jobportal.servlet;

import com.example.jobportal.dao.JobDAO;
import com.example.jobportal.model.Job;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/employer/jobs")
public class EmployerJobServlet extends HttpServlet {

    private JobDAO jobDAO = new JobDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get employerId from session (assuming login implemented later)
        HttpSession session = req.getSession();
        Integer employerId = (Integer) session.getAttribute("userId");

        if (employerId == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // List jobs posted by employer
            List<Job> jobs = jobDAO.listJobsByEmployer(employerId);
            req.setAttribute("jobs", jobs);

            req.getRequestDispatcher("/employer/employer-dashboard.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Cannot load employer jobs", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Integer employerId = (Integer) session.getAttribute("userId");

        if (employerId == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            if ("create".equals(action)) {

                Job job = new Job();
                job.setEmployerId(employerId);
                job.setTitle(req.getParameter("title"));
                job.setDescription(req.getParameter("description"));
                job.setRequirements(req.getParameter("requirements"));
                job.setSalary(req.getParameter("salary"));
                job.setLocation(req.getParameter("location"));

                jobDAO.createJob(job);
            }

            else if ("delete".equals(action)) {
                int jobId = Integer.parseInt(req.getParameter("jobId"));
                jobDAO.deleteJob(jobId, employerId);
            }

            resp.sendRedirect(req.getContextPath() + "/employer/jobs");

        } catch (Exception e) {
            throw new ServletException("Error handling employer action", e);
        }
    }
}
