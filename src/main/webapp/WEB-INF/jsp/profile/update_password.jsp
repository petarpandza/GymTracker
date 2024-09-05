<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Password</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update_password.css">
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<h1>Update Password</h1>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<div class="container">
    <form action="${pageContext.request.contextPath}/updatePassword" method="post">
        <div class="field">
            <label for="currentPassword">Current Password:</label>
            <input type="password" id="currentPassword" name="currentPassword" required>
        </div>
        <div class="field">
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div class="field">
            <label for="confirmNewPassword">Confirm New Password:</label>
            <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
        </div>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <button class="update-button" type="submit">Update Password</button>
    </form>
</div>
</body>
</html>
