<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Homepage</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homepage.css">
</head>
<body>
<div class="container">
    <h1>Welcome to GymTracker</h1>
    <div class="buttons">
        <a href="${pageContext.request.contextPath}/session" class="button">Start New Empty Workout</a>
        <a href="${pageContext.request.contextPath}/templates" class="button">Start from Template</a>
    </div>
    <div class="links">
        <a href="${pageContext.request.contextPath}/exercises" class="link">View Exercises</a>
        <a href="${pageContext.request.contextPath}/sessions" class="link">View Old Workouts</a>
        <a href="${pageContext.request.contextPath}/profile" class="link">View Profile</a>
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="submit" value="Logout" class="link">
        </form>
    </div>
</div>
</body>
</html>
