<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2><fmt:message key="page.change.password" bundle="${msg}"/></h2>
<form method="post" action="${pageContext.request.contextPath}/changePassword">
    <input type="password" required placeholder="<fmt:message key="placeholder.old.password" bundle="${msg}"/>"
           name="oldPassword">
    <input type="password" required placeholder="<fmt:message key="placeholder.new.password" bundle="${msg}"/>"
           name="newPassword1">
    <input type="password" required placeholder="<fmt:message key="placeholder.new.password" bundle="${msg}"/>"
           name="newPassword2">
    <input type="submit" value="<fmt:message key="page.change.password" bundle="${msg}"/>">
</form>
<c:if test="${error}">
    <p style="color:red"><fmt:message key="change.password.error" bundle="${msg}"/></p>
</c:if>
<c:if test="${success}">
    <p style="color:green"><fmt:message key="change.password.succes" bundle="${msg}"/></p>
</c:if>
