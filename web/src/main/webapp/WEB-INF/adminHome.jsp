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
<h2>Hello administrator, ${user.login}!</h2>
<h2>Привет администратор, ${user.firstName}!</h2>
<c:if test="${not empty orders}">
    <div>
        <table>
            <tr>
                <th><fmt:message key="table.order.date.in" bundle="${msg}"/></th>
                <th><fmt:message key="table.order.date.out" bundle="${msg}"/></th>
                <th>Login</th>
                <th><fmt:message key="table.order.type" bundle="${msg}"/></th>
                <th><fmt:message key="table.order.amount" bundle="${msg}"/></th>
                <th><fmt:message key="table.order.decision" bundle="${msg}"/></th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <form method="post" id="${order.orderId}">
                    <tr>
                        <td>${order.dateIn}</td>
                        <td>${order.dateOut}</td>
                        <td>${order.login}</td>
                        <td><c:choose><c:when test="${order.type == 'STANDARD'}"><fmt:message
                                key="order.room.type.standard" bundle="${msg}"/></c:when>
                            <c:when test="${order.type == 'DELUXE'}"><fmt:message key="order.room.type.deluxe"
                                                                                  bundle="${msg}"/></c:when>
                            <c:when test="${order.type == 'STUDIO'}"><fmt:message key="order.room.type.studio"
                                                                                  bundle="${msg}"/></c:when></c:choose></td>
                        <td>${order.amountOfRooms}</td>
                        <td>
                            <select form="${order.orderId}" name="decision" required>
                                <option value="${order.decision.toString()}" hidden>
                                    <c:choose><c:when test="${order.decision == 'AWAITING'}"><fmt:message
                                            key="order.room.decision.awaiting" bundle="${msg}"/></c:when>
                                        <c:when test="${order.decision == 'APPROVED'}"><fmt:message
                                                key="order.room.decision.approved" bundle="${msg}"/></c:when></c:choose>
                                </option>
                                <option value="AWAITING"><fmt:message key="order.room.decision.awaiting"
                                                                      bundle="${msg}"/></option>
                                <option value="APPROVED"><fmt:message key="order.room.decision.approved"
                                                                      bundle="${msg}"/></option>
                            </select>
                        </td>
                        <td>
                            <button type="submit" name="btn" value="${order.orderId}"><fmt:message key="btn.change"
                                                                                                   bundle="${msg}"/></button>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </div>
</c:if>
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
<p><a href="/hotel/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>
</body>
</html>
