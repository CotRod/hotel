<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="messages"/>

<html>
<head>
    <title><fmt:message key="page.login.log.in" bundle="${messages}"/></title>
</head>
<body>
<%@include file="LanguageSubPage.jsp"%>
<h2><fmt:message key="page.login.log.in" bundle="${messages}"/></h2>
<form method="post">
    <input type="text" required placeholder="<fmt:message key="placeholder.login" bundle="${messages}"/>" name="login">
    <input type="password" required placeholder="<fmt:message key="placeholder.password" bundle="${messages}"/>"
           name="password">
    <input type="submit" value="<fmt:message key="page.login.log.in" bundle="${messages}"/>">
</form>
<c:if test="${error}">
    <p style="color:red"><fmt:message key="page.login.error" bundle="${messages}"/></p>
</c:if>
<p><a href="${pageContext.request.contextPath}/signup"><fmt:message key="to.page.signup" bundle="${messages}"/></a></p>

</body>
</html>
