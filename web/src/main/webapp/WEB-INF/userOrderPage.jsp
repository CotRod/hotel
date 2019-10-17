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
    <c:when test="${not empty rooms}">
        <div style="display: inline-block">
            <form method="post">
                <fieldset>
                    <legend>Форма заказа</legend>
                    <p><label for="dateIn">Дата заселение</label> <input required type="date" name="dateIn" id="dateIn">
                    </p>
                    <p><label for="dateOut">Дата выселения</label> <input required type="date" name="dateOut"
                                                                          id="dateOut">
                    </p>
                    <table>
                        <tr>
                            <th>Chose</th>
                            <th>Type</th>
                            <th>Amount of rooms</th>
                            <th>Quantity</th>
                        </tr>
                        <c:forEach var="room" items="${rooms}">
                            <tr>
                                <td><input type="radio" required name="radio" value="${room.id}"></td>
                                <td><c:choose><c:when test="${room.type == 'STANDARD'}">Standard</c:when>
                                    <c:when test="${room.type == 'DELUXE'}">Deluxe</c:when>
                                    <c:when test="${room.type == 'STUDIO'}">Studio</c:when></c:choose></td>
                                <td>${room.amountOfRooms}</td>
                                <td>${room.quantity}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </fieldset>
                <input type="submit"> <input type="reset">
            </form>
        </div>
    </c:when>
    <c:when test="${empty rooms}">
        <p>Нет свободных номеров</p>
    </c:when>

</c:choose>

<p><a href="${pageContext.request.contextPath}/profile/user/home"><fmt:message key="to.page.home" bundle="${msg}"/></a>
</p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="to.logout" bundle="${msg}"/></a></p>
</body>
</html>