<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="resources" var="locale"/>

<html>
<head>
    <title>Manager Page</title>
</head>
<body>
<h6>${sessionScope.userLogin}</h6>


<form method="get" action="${pageContext.request.contextPath}/helloServlet">
    <input type="hidden" name="action" value="logout">
    <button type="submit"> <fmt:message bundle="${locale}" key="label.Logout"/> </button>
</form>
<form method="get" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="viewAllUnAcceptedOrders">
    <button type="submit">View Orders</button>
</form>


<form method="get" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="viewOrders">
    <input type="hidden" name="viewSuchOrders" value="approved">
    <button type="submit">Approved Orders</button>
</form>

<form method="get" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="viewOrders">
    <input type="hidden" name="viewSuchOrders" value="declined">
    <button type="submit">Declined Orders</button>
</form>

</body>
</html>
