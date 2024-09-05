<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Track Your Workout</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/create_template.css">
    <script src="${pageContext.request.contextPath}/js/template_logic.js"></script>
</head>
<body>
<div class="container">
    <h1>Create New Template</h1>
    <form action="${pageContext.request.contextPath}/saveTemplate" method="post">
        <div class="form-group">
            <label for="templateName">Template Name:</label>
            <input type="text" id="templateName" name="templateName" required>
        </div>
        <div class="form-group">
            <label for="exerciseSelect">Add Exercise:</label>
            <select id="exerciseSelect">
                <c:forEach var="exercise" items="${exercises}">
                    <option value="${exercise.exerciseId}">${exercise.name}</option>
                </c:forEach>
            </select>
            <button type="button" onclick="addExercise()">+</button>
        </div>
        <div id="exerciseList" class="exercise-list"></div>
        <button type="submit" class="submit-button">Save Template</button>
    </form>
</div>
</body>
</html>
