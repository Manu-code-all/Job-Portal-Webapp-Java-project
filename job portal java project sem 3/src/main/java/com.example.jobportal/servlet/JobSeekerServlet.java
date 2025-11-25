package com.example.jobportal.servlet;

import com.example.jobportal.dao.ApplicationDAO;
import com.example.jobportal.dao.JobDAO;
import com.example.jobportal.model.Application;
import com.example.jobportal.model.Job;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/jobseeker/jobs")
public class JobSeekerServlet extends HttpServlet {

    private JobDAO jobDAO = new JobDAO();
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // For job seeker show APPROVED jobs only
            List<Job> jobs = jobDAO.listApprovedJobs();
            req.setAttribute("jobs", jobs);

            req.getRequestDispatcher("/jobseeker/jobseeker-dashboard.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error loading jobs", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer seekerId = (Integer) session.getAttribute("userId");

        if (seekerId == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        try {
            if ("apply".equals(action)) {

                Application app = new Application();
                app.setJobId(Integer.parseInt(req.getParameter("jobId")));
                app.setSeekerId(seekerId);
                app.setResumePath(req.getParameter("resume"));   // later: file upload
                app.setCoverLetter(req.getParameter("coverLetter"));

                applicationDAO.apply(app);
            }

            resp.sendRedirect(req.getContextPath() + "/jobseeker/jobs");

        } catch (Exception e) {
            throw new ServletException("Error applying to job", e);
        }
    }
}
