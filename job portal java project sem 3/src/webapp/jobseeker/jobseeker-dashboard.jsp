<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Job Seeker Dashboard</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #999; padding: 8px; }
        h2 { color: #0A3D62; }
    </style>
</head>
<body>

<h1>Job Seeker Dashboard</h1>

<h2>Available Jobs</h2>

<table>
    <tr>
        <th>ID</th><th>Title</th><th>Description</th><th>Location</th><th>Apply</th>
    </tr>

    <c:forEach var="j" items="${jobs}">
        <tr>
            <td>${j.id}</td>
            <td>${j.title}</td>
            <td>${j.description}</td>
            <td>${j.location}</td>

            <td>
                <form action="<c:url value='/jobseeker/jobs'/>" method="post">
                    <input type="hidden" name="action" value="apply">
                    <input type="hidden" name="jobId" value="${j.id}">
                    Resume: <input type="text" name="resume" placeholder="resume path">
                    <br>
                    Cover Letter:
                    <input type="text" name="coverLetter">
                    <button type="submit">Apply</button>
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
