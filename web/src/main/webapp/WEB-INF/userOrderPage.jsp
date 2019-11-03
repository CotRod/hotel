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
<c:choose>
    <c:when test="${orderId>0}">
        <p style="color:green"><fmt:message key="page.make.order.success" bundle="${msg}"/></p>
    </c:when>
    <c:when test="${orderId<0}">
        <p style="color:red"><fmt:message key="page.make.order.no.rooms" bundle="${msg}"/></p>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${not empty rooms}">
        <div style="display: inline-block">
            <form method="post">
                <fieldset>
                    <legend><fmt:message key="page.make.order.name.form" bundle="${msg}"/></legend>
                    <p><label for="dateIn"><fmt:message key="table.order.date.in" bundle="${msg}"/></label> <input
                            required type="date" name="dateIn" id="dateIn">
                    </p>
                    <p><label for="dateOut"><fmt:message key="table.order.date.out" bundle="${msg}"/></label> <input
                            required type="date" name="dateOut"
                            id="dateOut">
                    </p>
                    <table>
                        <tr>
                            <th><fmt:message key="table.order.choice" bundle="${msg}"/></th>
                            <th><fmt:message key="table.order.type" bundle="${msg}"/></th>
                            <th><fmt:message key="table.order.amount" bundle="${msg}"/></th>
                            <th><fmt:message key="table.order.quantity" bundle="${msg}"/></th>
                        </tr>
                        <c:forEach var="room" items="${rooms}">
                            <tr>
                                <td><input type="radio" required name="radio" value="${room.id}"></td>
                                <td><c:choose><c:when test="${room.type == 'STANDARD'}"><fmt:message
                                        key="order.room.type.standard" bundle="${msg}"/></c:when>
                                    <c:when test="${room.type == 'DELUXE'}"><fmt:message key="order.room.type.deluxe"
                                                                                         bundle="${msg}"/></c:when>
                                    <c:when test="${room.type == 'STUDIO'}"><fmt:message key="order.room.type.studio"
                                                                                         bundle="${msg}"/></c:when></c:choose></td>
                                <td>${room.amountOfRooms}</td>
                                <td>${room.quantity}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </fieldset>
                <input type="submit" value="<fmt:message key="btn.submit" bundle="${msg}"/>"> <input type="reset"
                                                                                                     value="<fmt:message key="btn.reset" bundle="${msg}"/>">
            </form>
        </div>
    </c:when>
    <c:when test="${empty rooms}">
        <c:if test="${empty orderId}">
            <p><fmt:message key="page.make.order.no.rooms" bundle="${msg}"/></p>
        </c:if>
    </c:when>

</c:choose>

<p><a href="${pageContext.request.contextPath}/profile/user/home"><fmt:message key="to.page.home" bundle="${msg}"/></a>
</p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>
</body>
</html>