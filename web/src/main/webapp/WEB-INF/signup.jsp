<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>

<h2>Sign up</h2>
<form method="post">
    <input type="text" required placeholder="login" name="login">
    <input type="password" required placeholder="password" name="password">
    <input type="submit" value="Sign up">
</form>
<c:if test="${error}">
    <p style="color:red">${errorMsg}</p>
</c:if>
<p><a href="/hotel/login"> To log in </a> </p>

</body>
</html>
