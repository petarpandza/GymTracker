<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exercise List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/exercises.css">
    <script>
        function showTab(type) {
            let tabs = document.getElementsByClassName('tab-content');
            for (let i = 0; i < tabs.length; i++) {
                tabs[i].style.display = 'none';
            }
            let buttons = document.getElementsByClassName('tab-button');
            for (let i = 0; i < buttons.length; i++) {
                buttons[i].classList.remove('pressed');
            }

            document.getElementById(type + '-button').classList.add('pressed');
            document.getElementById(type).style.display = 'block';
        }
        window.onload = function() {
            showTab('all');
        }
    </script>
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<div class="container">
    <h1>Exercises</h1>
    <div class="tabs">
        <button class="tab-button pressed" id="all-button" onclick="showTab('all')">All Exercises</button>
        <c:forEach var="type" items="${sortedExercises.keySet()}">
            <button class="tab-button" id="${type}-button" onclick="showTab('${type}')">${type}</button>
        </c:forEach>
    </div>
    <div id="all" class="tab-content">
        <c:forEach var="exercise" items="${allExercises}">
            <div class="exercise">
                <a href="${pageContext.request.contextPath}/exercise?id=${exercise.exerciseId}">${exercise.name}</a>
            </div>
        </c:forEach>
    </div>
    <c:forEach var="entry" items="${sortedExercises}">
        <div id="${entry.key}" class="tab-content" style="display:none;">
            <c:forEach var="exercise" items="${entry.value}">
                <div class="exercise">
                    <a href="${pageContext.request.contextPath}/exercise?id=${exercise.exerciseId}">${exercise.name}</a>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
</div>
</body>
</html>
