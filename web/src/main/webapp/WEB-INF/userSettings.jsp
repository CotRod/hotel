<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="msg"/>
<html>
<head>
    <title><fmt:message key="page.settings" bundle="${msg}"/></title>
</head>
<body>
<%@include file="LanguageSubPage.jsp" %>
<h1><fmt:message key="page.settings" bundle="${msg}"/></h1>
<%@include file="changePassword.jsp" %>
<p><a href="${pageContext.request.contextPath}/profile/user/home"><fmt:message key="to.page.home" bundle="${msg}"/></a>
</p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>
</body>
</html>
