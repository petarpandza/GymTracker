<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Templates</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/templates.css">
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<h1>Your Templates</h1>
<div class="container">
    <c:if test="${empty user.trainingTemplates}">
        <p>You have no templates yet.</p><br>
    </c:if>
    <c:forEach var="template" items="${user.trainingTemplates}">
        <div class="template">
            <div class="template-header">
                <span class="template-name">${template.name}</span>
                <div class="template-buttons">
                    <a href="${pageContext.request.contextPath}/session?templateId=${template.id}" class="start-button">Start Workout</a>
                    <form method="post" action="${pageContext.request.contextPath}/deleteTemplate">
                        <input type="hidden" name="id" value="${template.id}">
                        <button type="submit" class="delete-button">Delete</button>
                    </form>
                </div>
            </div>
            <div class="template-exercises">
                <c:forEach var="exercise" items="${template.exercises}">
                    <span class="exercise-name">${exercise.name}</span><br>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
    <a href="createTemplate" class="create-button">Create New Template</a>
</div>
</body>
</html>
