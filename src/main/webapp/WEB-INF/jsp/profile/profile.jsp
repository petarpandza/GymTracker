<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
    <script>
        function enableEditing() {
            document.getElementById("email").disabled = false;
            document.getElementById("username").disabled = false;
            document.getElementById("gender-select").style.display = "block";
            document.getElementById("save-button").style.display = "inline-block";

            document.getElementById("delete-button").style.display = "none";
        }
    </script>
</head>
<body>
<div class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" alt="GymTracker Logo"></a>
</div>
<h1>Your Profile</h1>
<div class="container">
    <div class="profile-info">
        <form action="${pageContext.request.contextPath}/updateProfile" method="post">
            <div class="field">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" disabled>
            </div>
            <div class="field">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${user.username}" disabled>
            </div>
            <div id="gender-select" class="field" style="display:none;">
                <label for="gender">Gender:</label>
                <select id="gender" name="gender">
                    <option value="1" ${user.gender == 1 ? 'selected="selected"' : ""}>Male</option>
                    <option value="2" ${user.gender == 2 ? 'selected="selected"' : ""}>Female</option>
                    <option value="3" ${user.gender == 3 ? 'selected="selected"' : ""}>Other</option>
                    <option value="0" ${user.gender == null ? 'selected="selected"' : ""}>Prefer not to say</option>
                </select>
            </div>
            <c:if test="${not empty error}">
                <div class="error-message">
                        ${error}
                </div>
            </c:if>
            <button type="button" onclick="enableEditing()">Edit</button>
            <a href="${pageContext.request.contextPath}/deleteAccount" id="delete-button" class="delete-button">Delete account</a>
            <button type="submit" id="save-button" class="save-button" style="display:none;">Save</button>
        </form>
    </div>
    <div class="password-change">
        <a href="${pageContext.request.contextPath}/updatePassword">Change your password</a>
    </div>
</div>
</body>
</html>
