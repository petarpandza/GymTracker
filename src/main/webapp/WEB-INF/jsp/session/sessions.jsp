<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>All sessions</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sessions.css">
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<c:if test="${not empty user.trainingSessions}">
    <p>Workout frequency</p>
    <img src="${pageContext.request.contextPath}/graphics/workouts" alt="Graph of past workouts" class="graph-image">
</c:if>
<div class="container">
    <c:if test="${empty user.trainingSessions}">
        <p>No workouts for now. <a href="${pageContext.request.contextPath}/session">Get to work!</a></p>
    </c:if>
    <c:if test="${not empty user.trainingSessions}">
        <p>Marvel at your hard work!</p>
    </c:if>
    <c:forEach var="session" items="${user.trainingSessions}">
        <div class="session">
            <div class="session-details">
                <a class="session-name" href="${pageContext.request.contextPath}/sessionDetails?id=${session.trainingSessionId}">
                    <c:out value="${session.sessionName}"/>
                </a>
                <span class="session-time"><c:out value="${session.startTime}"/></span>
                <span class="session-duration"><c:out value="${session.duration}"/>m</span>
            </div>
            <div class="session-actions">
                <a class="edit-link" href="${pageContext.request.contextPath}/editSession?id=${session.trainingSessionId}">Edit</a>
                <form method="post" action="${pageContext.request.contextPath}/deleteSession?id=${session.trainingSessionId}" class="inline">
                    <button type="submit" class="delete-link">Delete</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
