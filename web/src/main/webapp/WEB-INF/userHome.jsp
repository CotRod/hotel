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
<style>
    table {
        text-align: center;
        border-collapse: collapse;
    }

    td {
        padding: 10px;
        border-top: 1px solid black
    }

    .navigation {
        float: left;
        margin: 15px;
    }
</style>
<%@include file="LanguageSubPage.jsp" %>
<h2><fmt:message key="page.home.welcome" bundle="${msg}"/>, ${user.firstName}!</h2>


<p><a href="${pageContext.request.contextPath}/profile/user/order"><fmt:message key="to.order.page"
                                                                                bundle="${msg}"/></a></p>
<c:choose>
    <c:when test="${empty orders}"><p>Вы еще не заказали номер</p></c:when>
    <c:when test="${not empty orders}">
        <table>
            <tr>
                <th><fmt:message key="table.order.date.in" bundle="${msg}"/></th>
                <th><fmt:message key="table.order.date.out" bundle="${msg}"/></th>
                <th><fmt:message key="table.order.type" bundle="${msg}"/></th>
                <th><fmt:message key="table.order.amount" bundle="${msg}"/></th>
                <th><fmt:message key="table.order.decision" bundle="${msg}"/></th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.dateIn}</td>
                    <td>${order.dateOut}</td>
                    <td><c:choose><c:when test="${order.type == 'STANDARD'}"><fmt:message key="order.room.type.standard"
                                                                                          bundle="${msg}"/></c:when>
                        <c:when test="${order.type == 'DELUXE'}"><fmt:message key="order.room.type.deluxe"
                                                                              bundle="${msg}"/></c:when>
                        <c:when test="${order.type == 'STUDIO'}"><fmt:message key="order.room.type.studio"
                                                                              bundle="${msg}"/></c:when></c:choose></td>
                    <td>${order.amountOfRooms}</td>
                    <td><c:choose><c:when test="${order.decision == 'AWAITING'}"><fmt:message
                            key="order.room.decision.awaiting" bundle="${msg}"/></c:when>
                        <c:when test="${order.decision == 'APPROVED'}"><fmt:message key="order.room.decision.approved"
                                                                                    bundle="${msg}"/></c:when></c:choose></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
</c:choose>
<form method="post" style="overflow: auto">
    <div style="margin-left: 140px;">
        <c:if test="${pageNum>0}">
            <div class="navigation">
                <button type="submit" formmethod="post" name="nav" value="prev"><<</button>
            </div>
        </c:if>
        <div class="navigation"><p>${pageNum + 1}</p></div>
        <c:if test="${notLast}">
            <div class="navigation">
                <button type="submit" formmethod="post" name="nav" value="next">>></button>
            </div>
        </c:if>
    </div>
</form>
<p><a href="${pageContext.request.contextPath}/profile/user/settings"><fmt:message key="to.settings.page"
                                                                                   bundle="${msg}"/></a></p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>

</body>
</html>