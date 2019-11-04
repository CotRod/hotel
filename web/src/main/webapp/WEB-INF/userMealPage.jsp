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
<style>
    table {
        text-align: center;
        border-collapse: collapse;
    }

    fieldset {
        text-align: -webkit-center;
        margin-bottom: 15px;
    }

    td {
        border-top: 1px solid black
    }
</style>

<p style="color:green"><fmt:message key="page.make.order.success" bundle="${msg}"/></p>
<h2>Выбирите тип питания</h2>
<form method="post">
    <input type="checkbox" name="meals" value="BREAKFAST" id="check1"/><label for="check1">Завтрак</label><Br>
    <input type="checkbox" name="meals" value="LUNCH" id="check2"/><label for="check2">Обед</label><Br>
    <input type="checkbox" name="meals" value="DINNER" id="check3"/><label for="check3">Ужин</label><Br>
    <input type="submit">
</form>


<p><a href="${pageContext.request.contextPath}/profile/user/home"><fmt:message key="to.page.home" bundle="${msg}"/></a>
</p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>
</body>
</html>