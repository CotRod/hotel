<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="messages"/>
<html>
<head>
    <title><fmt:message key="page.name.home" bundle="${messages}"/></title>
</head>
<body>

<%@include file="LanguageSubPage.jsp"%>
<h2><fmt:message key="page.name.home" bundle="${messages}"/></h2>
<form method="post">
    <input type="password" required placeholder="<fmt:message key="page.change.oldpass" bundle="${messages}"/>" name="oldPassword">
    <input type="password" required placeholder="<fmt:message key="page.change.newpass" bundle="${messages}"/>" name="newPassword1">
    <input type="password" required placeholder="<fmt:message key="page.change.newpass" bundle="${messages}"/>" name="newPassword2">
    <input type="submit" value="<fmt:message key="page.name.change.password" bundle="${messages}"/>">
</form>

<c:if test="${error}">
    <p style="color:red"><fmt:message key="page.change.error" bundle="${messages}"/></p>
</c:if>
<c:if test="${success}">
    <p style="color:green"><fmt:message key="page.change.succes" bundle="${messages}"/></p>
</c:if>

<p><a href="/hotel/userHome"><fmt:message key="page.name.home" bundle="${messages}"/></a></p>
<p><a href="/hotel/logout"><fmt:message key="to.logout" bundle="${messages}"/></a></p>
</body>
</html>
