<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="messages"/>
<html>
<head>
    <title><fmt:message key="page.name.home" bundle="${messages}"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%@include file="LanguageSubPage.jsp" %>
<h2>Hello user, ${login}!</h2>
<h2>Привет юзер, ${role}!</h2>
<p><a href="/hotel/logout"><fmt:message key="to.logout" bundle="${messages}"/></a></p>

</body>
</html>