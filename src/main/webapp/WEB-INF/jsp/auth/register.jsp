<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    var usernameParam = request.getParameter("username");
    var emailParam = request.getParameter("email");
    var error = request.getAttribute("error");
    String username = usernameParam != null ? usernameParam : "";
    String email = emailParam != null ? emailParam : "";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration.css">
    <title>Register</title>
</head>
<body>
<div class="form-container">
    <h2>Register</h2>
    <form action="${pageContext.request.contextPath}/register" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="<%=username%>" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%=email%>" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>

        <label for="gender">Gender:</label>
        <select id="gender" name="gender">
            <option value="" disabled selected></option>
            <option value="1">Male</option>
            <option value="2">Female</option>
            <option value="3">Other</option>
            <option value="0">Prefer not to say</option>
        </select>

        <%
            if (error != null) {
                out.println("<p class='error'>"+error+"</p>");
            }
        %>

        <input type="submit" value="Register">
    </form>
    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
</div>
</body>
</html>
