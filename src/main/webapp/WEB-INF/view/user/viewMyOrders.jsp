<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>View My Orders</title>
</head>
<body>


<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">Passport</th>
        <th scope="col">From Date</th>
        <th scope="col">To Date</th>
        <th scope="col">With driver</th>
        <th scope="col">Receipt</th>
        <th scope="col">Car</th>
        <th scope="col">Class</th>
        <th scope="col">Brand</th>
        <th scope="col">Feedback</th>
        <th scope="col">Approved</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="listOrders" items="${sessionScope.myPersonalOrders}">

    <tr>
        <td>${listOrders.order.passport}</td>
        <td>${listOrders.order.dateFrom}</td>
        <td>${listOrders.order.dateTo}</td>
        <td>${listOrders.order.withDriver}</td>
        <td>${listOrders.order.receipt}</td>
        <td>${listOrders.car.name}</td>
        <td>${listOrders.car.carClass}</td>
        <td>${listOrders.car.brand}</td>
        <td>${listOrders.feedback}</td>
        <td>${listOrders.approved}</td>

    </tr>
    </c:forEach>
    </tbody>
</table>
<form method="get" >
    <input type="hidden" name="action" value="pagination">
    <ul class="pagination justify-content-center">
        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
        <li class="page-item"><a class="page-link" href="${pageContext.request.contentType}/car/helloServlet?action=pagination&required=viewMyOrders&page=1">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item"><a class="page-link" href="#">Next</a></li>
    </ul>
</form>

<a href="${pageContext.request.contentType}/car/view/user/user.jsp">Go Back</a>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

</body>
</html>
