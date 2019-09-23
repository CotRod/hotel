<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h2>Log in</h2>
<form method="post">
    <input type="text" required placeholder="login" name="login">
    <input type="password" required placeholder="password" name="password">
    <input type="submit" value="Log in">
</form>
<c:if test="${errorNum==1}">
    <p style="color:red">${errorMsg}</p>
</c:if>
<p><a href="/hotel/signup"> To sign up </a> </p>

</body>
</html>
