<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update_password.css">
    <title>Delete Account</title>
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<div class="container">
    <h1>Confirm Account Deletion</h1>
    <form action="${pageContext.request.contextPath}/deleteAccount" method="post">
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <c:if test="${not empty error}">
            <br>
            <div class="error">${error}</div>
        </c:if>
        <button class="delete-button" type="submit">Delete Account</button>
    </form>
</div>
</body>
</html>
