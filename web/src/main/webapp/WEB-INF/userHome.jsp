<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="msg"/>
<html>
<head>
    <title><fmt:message key="page.home" bundle="${msg}"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%@include file="LanguageSubPage.jsp" %>
<h2><fmt:message key="page.home.welcome" bundle="${msg}"/>, ${user.firstName}!</h2>
<p><a href="${pageContext.request.contextPath}/profile/user/order"><fmt:message key="to.order.page"
                                                                                bundle="${msg}"/></a></p>

<p><a href="${pageContext.request.contextPath}/profile/user/settings"><fmt:message key="to.settings.page"
                                                                                   bundle="${msg}"/></a></p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>

</body>
</html>