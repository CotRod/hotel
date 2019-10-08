<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="messages"/>
<html>
<head>
    <title><fmt:message key="page.name.signup" bundle="${messages}"/></title>
</head>
<body>
<%@include file="LanguageSubPage.jsp"%>
<h2><fmt:message key="page.name.signup" bundle="${messages}"/></h2>
<form method="post">
    <input type="text" required placeholder="<fmt:message key="plcholder.login" bundle="${messages}"/>" name="login">
    <input type="password" required placeholder="<fmt:message key="plcholder.password" bundle="${messages}"/>" name="password">
    <input type="submit" value="<fmt:message key="page.name.signup" bundle="${messages}"/>">
</form>
<c:if test="${error}">
    <p style="color:red"><fmt:message key="page.signup.error" bundle="${messages}"/></p>
</c:if>
<p><a href="/hotel/login"><fmt:message key="to.page.login" bundle="${messages}"/></a> </p>

</body>
</html>
