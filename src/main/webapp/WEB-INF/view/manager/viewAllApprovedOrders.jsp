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
        <td>${listOrders.order.passport}</td>
        <td>${listOrders.order.receipt}</td>
        <td>${listOrders.order.dateFrom}</td>
        <td>${listOrders.order.dateTo}</td>
        <td>${listOrders.order.withDriver}</td>
        <td>${listOrders.car.name}</td>
        <td>${listOrders.car.carClass}</td>
        <td>${listOrders.car.brand}</td>
        </c:forEach>
    </tr>

    </tbody>
</table>
<form method="get" >
    <input type="hidden" name="action" value="pagination">
    <ul class="pagination justify-content-center">
        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
        <li class="page-item"><a class="page-link" href="${pageContext.request.contentType}/car/helloServlet?action=pagination&required=viewAllUsersOrders&page=1">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item"><a class="page-link" href="#">Next</a></li>
    </ul>
</form>
<a href="${pageContext.request.contentType}/car/view/manager/manager.jsp">Go Back</a>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>
</body>
</html>
