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
</style>
<%@include file="LanguageSubPage.jsp" %>
<h2>Hello administrator, ${user.login}!</h2>
<h2>Привет администратор, ${user.firstName}!</h2>
<c:if test="${not empty orders}">
    <div>
        <table>
            <tr>
                <th>Date In</th>
                <th>Date Out</th>
                <th>Login</th>
                <th>Type</th>
                <th>Amount of rooms</th>
                <th>Decision</th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <form method="post" id="${order.orderId}">
                    <tr>
                        <td>${order.dateIn}</td>
                        <td>${order.dateOut}</td>
                        <td>${order.login}</td>
                        <td><c:choose><c:when test="${order.type == 'STANDARD'}">Standard</c:when>
                            <c:when test="${order.type == 'DELUXE'}">Deluxe</c:when>
                            <c:when test="${order.type == 'STUDIO'}">Studio</c:when></c:choose></td>
                        <td>${order.amountOfRooms}</td>
                        <td>
                            <c:choose><c:when test="${order.decision == 'AWAITING'}">Awaiting</c:when>
                                <c:when test="${order.decision == 'APPROVED'}">Approved</c:when></c:choose>
                            <select form="${order.orderId}" name="decision" required>
                                <option value="AWAITING">Awaiting</option>
                                <option value="APPROVED">Approved</option>
                            </select>
                        </td>
                        <td>
                            <button type="submit" name="btn" value="${order.orderId}">Изменить</button>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </div>
</c:if>
<p><a href="/hotel/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>
</body>
</html>
