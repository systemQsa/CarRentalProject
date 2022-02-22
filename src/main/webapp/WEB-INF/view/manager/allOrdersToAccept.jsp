<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="resources" var="locale"/>
<html>
<head>
    <title>ALL NOT APPROVED ORDERS</title>
</head>
<body>
<p>${requestScope.login}</p>
<table>
    <thead>
    <tr>User login</tr>
    <tr>Passport</tr>
    <tr>From Date</tr>
    <tr>To Date</tr>
    <tr>With Driver</tr>
    <tr>Total Price</tr>
    </thead>
    <tbody>

    <c:forEach  var="order" items="${applicationScope.orderList}">
        <tr>
            <td>${order.userLogin}</td>
            <td>${order.carId}</td>
            <td>${order.passport}</td>
            <td>${order.fromDate}</td>
            <td>${order.toDate}</td>
            <td>${order.withDriver}</td>
            <td>${order.receipt}</td>

            <td><form method="post" action="${pageContext.request.contentType}/car/helloServlet">
                <input type="hidden" name="action" value="acceptOrder">
                <input type="hidden" name="acceptUserOrder" value="${order}">
                <input type="hidden" name="approved" value="yes">
                <input type="text" name="feedback">
                <button type="submit">Accept order</button>
            </form></td>
            <td><form method="post" action="${pageContext.request.contentType}/car/helloServlet">
                <input type="hidden" name="action" value="declineOrder">
                <input type="hidden" name="approved" value="no">
                <input type="text" name="feedback">
                <input type="hidden" name="declineUserOrder" value="${order}">
                <button type="submit">Decline Order</button>
            </form></td>
            <td>
                <c:if  test="${sessionScope.orders != null}">
                    <c:forEach var="map" items="${sessionScope.orders}">
                        <c:if test="${map.key eq order}">
                            <p>${map.value}</p>
                        </c:if>
                    </c:forEach>

                </c:if></td>
        </tr>
    </c:forEach>

    </tbody>
</table>
</body>
</html>
