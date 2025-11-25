package com.example.jobportal.servlet;

import com.example.jobportal.dao.UserDAO;
import com.example.jobportal.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // fetch users and forward to admin-dashboard.jsp
        // Example omitted for brevity
        req.getRequestDispatcher("/admin/admin-dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // handle create/update user
        String action = req.getParameter("action");
        try {
            if ("create".equals(action)) {
                User u = new User();
                u.setName(req.getParameter("name"));
                u.setEmail(req.getParameter("email"));
                u.setPasswordHash(req.getParameter("password")); // hash in real app
                u.setRole(req.getParameter("role"));
                userDAO.createUser(u);
            }
            resp.sendRedirect(req.getContextPath()+"/admin/users");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
