<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${trainingSession.sessionName}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/session.css">
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<div class="container">
    <div class="workout-details">
        <div class="detail">
            <strong>Name:</strong> ${trainingSession.sessionName}
        </div>
        <div class="detail">
            <strong>Date:</strong> ${trainingSession.startTime}
        </div>
        <div class="detail">
            <strong>Duration:</strong> ${trainingSession.duration} minutes
        </div>
    </div>
    <div class="exercises">
        <c:forEach var="exercise" items="${sortedSets}">
            <div class="exercise">
                <div class="exercise-header">
                    <h3 class="exercise-name">${exercise.key.name}</h3>
                </div>
                <div class="sets">
                    <c:forEach var="set" items="${exercise.value}">
                        <div class="set">
                            <div><strong>Weight:</strong> ${set.weight} kg</div>
                            <div><strong>Reps:</strong> ${set.repetitions}</div>
                            <div>${set.setType.name}</div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
