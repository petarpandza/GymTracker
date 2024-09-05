<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="container">
    <h1>Admin Panel</h1>

    <a href="${pageContext.request.contextPath}/logout">Log out</a>

    <div class="section">
        <h2>Add Exercise</h2>
        <form action="${pageContext.request.contextPath}/admin/createExercise" method="post">
            <label for="exerciseName">Exercise Name:</label>
            <input type="text" id="exerciseName" name="exerciseName" required>
            <label for="exerciseDescription">Exercise description:</label>
            <input type="text" id="exerciseDescription" name="exerciseDescription" required>
            <label for="exerciseType">Exercise type:</label>
            <select id="exerciseType" name="exerciseType">
                <c:forEach var="exerciseType" items="${exerciseTypes}">
                    <option value="${exerciseType.id}">${exerciseType.name}</option>
                </c:forEach>
            </select>
            <button type="submit">Add Exercise</button>
        </form>
    </div>

    <div class="section">
        <h2>Delete Exercise</h2>
        <ul class="exercise-list">
            <c:forEach var="exercise" items="${exercises}">
                <li>
                        ${exercise.name}
                    <form action="${pageContext.request.contextPath}/admin/deleteExercise" method="post" class="inline-form">
                        <input type="hidden" name="exerciseId" value="${exercise.exerciseId}">
                        <button type="submit">Delete</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
