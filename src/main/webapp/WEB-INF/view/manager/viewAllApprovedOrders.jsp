<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View All Approved Orders</title>
</head>
<body>
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">Login</th>
        <th scope="col">Passport</th>
        <th scope="col">Receipt</th>
        <th scope="col">From Date</th>
        <th scope="col">To Date</th>
        <th scope="col">With driver</th>
        <th scope="col">Car</th>
        <th scope="col">Class</th>
        <th scope="col">Brand</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="listOrders" items="${sessionScope.listOrders}">

    <tr>
        <th scope="row">${listOrders.login}</th>
        <td>${listOrders.passport}</td>
        <td>${listOrders.receipt}</td>
        <td>${listOrders.fromDate}</td>
        <td>${listOrders.toDate}</td>
        <td>${listOrders.withDriver}</td>
        <td>${listOrders.name}</td>
        <td>${listOrders.carClass}</td>
        <td>${listOrders.brand}</td>
        </c:forEach>
    </tr>

    </tbody>
</table>
<a href="${pageContext.request.contentType}/car/view/manager/manager.jsp">Go Back</a>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>
</body>
</html>
