<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="msg"/>
<html>
<head>
    <title><fmt:message key="page.home" bundle="${msg}"/></title>
</head>
<body>
<%@include file="LanguageSubPage.jsp"%>
<h2>Hello administrator, ${user.login}!</h2>
<h2>Привет администратор, ${user.firstName}!</h2>
<c:if test="${not empty users}">
    <form method="post">
    <ul>
<c:forEach var="user" items="${users}">
    <li><c:out value="${user.login}"/>
        <button type="submit" name="btn" value="${user.id}">X</button>
    </li>
</c:forEach>
    </ul>
    </form>
</c:if>
<p><a href="/hotel/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>
</body>
</html>
