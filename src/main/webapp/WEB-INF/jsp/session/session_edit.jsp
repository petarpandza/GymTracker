<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editing workout: ${trainingSession.sessionName}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/session.css">
    <script src="${pageContext.request.contextPath}/js/session_logic.js"></script>
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<div class="container">
    <form action="${pageContext.request.contextPath}/updateSession?id=${trainingSession.trainingSessionId}"
          method="post">
        <div class="name">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${trainingSession.sessionName}" required>
        </div>
        <div class="exercise-select">
            <label for="exerciseSelect">Choose an exercise:</label>
            <select id="exerciseSelect">
                <c:forEach items="${exercises}" var="exercise">
                    <option value="${exercise.exerciseId}">${exercise.name}</option>
                </c:forEach>
            </select>
            <button type="button" onclick="addExercise()">Add Exercise</button>
        </div>
        <div id="exercises" class="exercises">
            <c:forEach items="${sortedSets}" var="entry">
                <div id="exercise-${entry.key.exerciseId}" class="exercise">
                    <div class="exercise-header">
                        <h3 class="exercise-name">${entry.key.name}</h3>
                        <div class="exercise-buttons">
                            <button type="button" onclick="addSet(${entry.key.exerciseId})">+</button>
                            <button type="button" onclick="removeExercise(${entry.key.exerciseId})">-</button>
                        </div>
                    </div>
                    <div id="sets-${entry.key.exerciseId}" class="sets">
                        <c:forEach items="${entry.value}" var="set">
                            <div id="set-${entry.key.exerciseId}-${set.id.setNumber}" class="set">
                                <label>Weight:</label>
                                <input value="${set.weight}" type="number"
                                       name="${entry.key.exerciseId}#${set.id.setNumber}#weight" step="0.5" min="0"
                                       required>
                                <label>Reps:</label>
                                <input value="${set.repetitions}" type="number"
                                       name="${entry.key.exerciseId}#${set.id.setNumber}#reps" min="1" required>
                                <label>Set Type:</label>
                                <select name="${entry.key.exerciseId}#${set.id.setNumber}#setType" required>
                                    <option value="1" ${set.setType.setTypeId == 1 ? 'selected="selected"' : ""}>
                                        Regular
                                    </option>
                                    <option value="2" ${set.setType.setTypeId == 2 ? 'selected="selected"' : ""}>Drop
                                        set
                                    </option>
                                    <option value="3" ${set.setType.setTypeId == 3 ? 'selected="selected"' : ""}>
                                        Superset
                                    </option>
                                    <option value="4" ${set.setType.setTypeId == 4 ? 'selected="selected"' : ""}>To
                                        failure
                                    </option>
                                </select>
                                <button type="button" onclick="removeSet(${entry.key.exerciseId}, ${set.id.setNumber})">
                                    -
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
        <button type="submit">Save changes</button>
    </form>
</div>
</body>
</html>
