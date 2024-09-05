<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    var emailParam = request.getParameter("email");
    var email = emailParam != null ? emailParam : "";
    var error = request.getAttribute("error");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration.css">
    <title>Login</title>
</head>
<body>
<div class="form-container">
    <h2>Login</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%=email%>" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <%
            if (error != null) {
                out.println("<p class='error'>"+error+"</p>");
            }
        %>

        <input type="submit" value="Login">

    </form>

    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
</div>
</body>
</html>
