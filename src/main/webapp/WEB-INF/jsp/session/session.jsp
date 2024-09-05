<%@ page import="hr.fer.gymtracker.models.TrainingTemplate" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<%
    TrainingTemplate template = (TrainingTemplate) request.getAttribute("template");
    String currentDate = new SimpleDateFormat("dd.MM.yyyy.").format(new Date());
    String title = template == null ? currentDate + " workout." : template.getName();
%>
<head>
    <title>Track Your Workout</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/session.css">
    <script src="${pageContext.request.contextPath}/js/session_logic.js"></script>
</head>
<body>
<h1>Track Your Workout</h1>
<div class="container">
    <form action="${pageContext.request.contextPath}/saveSession" method="post">
        <div class="name">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="<%=title%>" required>
        </div>
        <div class="exercise-select">
            <label for="exerciseSelect">Choose an exercise:</label>
            <select id="exerciseSelect">
                <c:forEach items="${exercises}" var="exercise">
                    <option value="${exercise.exerciseId}">${exercise.name}</option>
                </c:forEach>
            </select>
            <button class="add-exercise" type="button" onclick="addExercise()">Add Exercise</button>
        </div>
        <div id="exercises" class="exercises">
            <c:if test="${not empty template}">
                <c:forEach items="${template.exercises}" var="exercise">
                    <div id="exercise-${exercise.exerciseId}" class="exercise">
                        <div class="exercise-header">
                            <h3 class="exercise-name">${exercise.name}</h3>
                            <div class="exercise-buttons">
                                <button type="button" onclick="addSet(${exercise.exerciseId})">+</button>
                                <button type="button" onclick="removeExercise(${exercise.exerciseId})">-</button>
                            </div>
                        </div>
                        <div id="sets-${exercise.exerciseId}" class="sets">
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <button type="submit">Finish Workout</button>
    </form>
</div>
</body>
</html>
