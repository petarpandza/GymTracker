<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exercise Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/exercise_details.css">
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<div class="container">
    <h1>${exercise.name}</h1>
    <p><strong>Type:</strong> ${exercise.exerciseType.name}</p>
    <p><strong>Description:</strong> ${exercise.description}</p>
    <a href="${pageContext.request.contextPath}/exercises" class="back-link">Back to Exercises</a>

    <div class="personal-bests">
        <h2>Personal Bests</h2>
        <c:choose>
            <c:when test="${empty personalBests}">
                <p>No records for this exercise.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>Reps</th>
                        <th>Weight</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="pb" items="${personalBests}">
                        <tr>
                            <td>${pb.id.bestReps}</td>
                            <td>${pb.bestWeight}</td>
                            <td>${pb.achievementDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <c:if test="${not empty personalBests}">
        <h2>Personal best progression</h2>
        <img src="${pageContext.request.contextPath}/graphics/personalBests?id=${exercise.exerciseId}" alt="Personal best progression" class="graph-image">
    </c:if>
</div>
</body>
</html>
