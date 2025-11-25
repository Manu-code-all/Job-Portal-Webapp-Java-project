<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }
        th, td { border: 1px solid #aaa; padding: 8px; }
        h2 { color: #0A3D62; }
    </style>
</head>
<body>

<h1>Admin Dashboard</h1>

<h2>User Management</h2>

<form action="<c:url value='/admin/users'/>" method="post">
    <input type="hidden" name="action" value="create">
    Name: <input type="text" name="name" required>
    Email: <input type="email" name="email" required>
    Password: <input type="text" name="password" required>
    Role: 
    <select name="role">
        <option>ADMIN</option>
        <option>EMPLOYER</option>
        <option>JOBSEEKER</option>
    </select>
    <button type="submit">Add User</button>
</form>

<br>

<table>
    <tr>
        <th>ID</th><th>Name</th><th>Email</th><th>Role</th>
    </tr>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td>${u.role}</td>
        </tr>
    </c:forEach>
</table>


<h2>Job Listing Management</h2>

<table>
    <tr>
        <th>ID</th><th>Title</th><th>Employer</th><th>Status</th><th>Actions</th>
    </tr>
    <c:forEach var="j" items="${jobs}">
        <tr>
            <td>${j.id}</td>
            <td>${j.title}</td>
            <td>${j.employerId}</td>
            <td>${j.status}</td>

            <td>
                <form action="<c:url value='/admin/jobs'/>" method="post" style="display:inline">
                    <input type="hidden" name="jobId" value="${j.id}">
                    <button name="action" value="approve">Approve</button>
                </form>

                <form action="<c:url value='/admin/jobs'/>" method="post" style="display:inline">
                    <input type="hidden" name="jobId" value="${j.id}">
                    <button name="action" value="reject">Reject</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
