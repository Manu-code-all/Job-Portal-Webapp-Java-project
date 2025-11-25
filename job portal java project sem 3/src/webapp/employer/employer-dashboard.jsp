<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employer Dashboard</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px;}
        th, td { border: 1px solid gray; padding: 8px; }
        h2 { color: #0A3D62; }
    </style>
</head>
<body>

<h1>Employer Dashboard</h1>

<h2>Post New Job</h2>

<form action="<c:url value='/employer/jobs'/>" method="post">
    <input type="hidden" name="action" value="create">

    Title: <input type="text" name="title" required><br><br>
    Description:<br>
    <textarea name="description" rows="3" cols="40"></textarea><br><br>

    Requirements:<br>
    <textarea name="requirements" rows="3" cols="40"></textarea><br><br>

    Salary: <input type="text" name="salary"><br><br>
    Location: <input type="text" name="location"><br><br>

    <button type="submit">Post Job</button>
</form>


<h2>Your Jobs</h2>

<table>
    <tr>
        <th>ID</th><th>Title</th><th>Status</th><th>Action</th>
    </tr>

    <c:forEach var="j" items="${jobs}">
        <tr>
            <td>${j.id}</td>
            <td>${j.title}</td>
            <td>${j.status}</td>

            <td>
                <form action="<c:url value='/employer/jobs'/>" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="jobId" value="${j.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
