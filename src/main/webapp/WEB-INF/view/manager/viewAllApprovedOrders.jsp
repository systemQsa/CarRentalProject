<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View All Approved Orders</title>
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

<c:choose>
    <c:when test="${not empty requestScope.listOrders}">
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
            <c:forEach var="listOrders" items="${requestScope.listOrders}">

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
<%--        <form method="get" >--%>
<%--            <input type="hidden" name="action" value="pagination">--%>
<%--            <ul class="pagination justify-content-center">--%>
<%--                <li class="page-item"><a class="page-link" href="#">Previous</a></li>--%>
<%--                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/helloServlet?action=pagination&required=viewAllUsersOrders&page=1">1</a></li>--%>
<%--                <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--                <li class="page-item"><a class="page-link" href="#">3</a></li>--%>
<%--                <li class="page-item"><a class="page-link" href="#">Next</a></li>--%>
<%--            </ul>--%>
<%--        </form>--%>

<c:if test="${requestScope.currentPage != 1}">
    <td><a href="?action=pagination&required=viewOrders&viewSuchOrders=approved&page=${requestScope.currentPage - 1}">${sessionScope.language['Previous']}</a></td>
</c:if>
<tr>
    <c:forEach begin="1" end="${requestScope.amountOfRecords.get()}" var="i">
        <c:choose>
            <c:when test="${requestScope.currentPage eq i}">
                <td>${i}</td>
            </c:when>
            <c:otherwise>
                <td><a href="?action=pagination&required=viewOrders&viewSuchOrders=approved&page=${i}">${i}</a></td>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</tr>
<c:if test="${requestScope.currentPage lt requestScope.amountOfRecords.get()}">
    <td><a href="?action=pagination&required=viewOrders&viewSuchOrders=${pageContext.request.getParameter("viewSuchOrders")}&page=${requestScope.currentPage + 1}">${sessionScope.language['Next']}</a></td>
</c:if>

<%--pagination--%>
<input type="hidden" name="action" value="pagination">
<ul class="pagination justify-content-center">
    <li class="page-item"><c:if test="${requestScope.currentPage != 1}">
        <td><a  class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=approved&page=${requestScope.currentPage - 1}">${sessionScope.language['Previous']}</a></td>
    </c:if></li>
    <li class="page-item"><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${pageContext.request.getParameter("viewSuchOrders")}&page=1">1</a></li>
    <li class="page-item"><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${pageContext.request.getParameter("viewSuchOrders")}&page=2">2</a></li>
    <li class="page-item"><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${pageContext.request.getParameter("viewSuchOrders")}&page=3">3</a></li>
    <li class="page-item">
        <c:if test="${requestScope.currentPage lt requestScope.amountOfRecords.get()}">
            <td><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${pageContext.request.getParameter("viewSuchOrders")}&page=${requestScope.currentPage + 1}">${sessionScope.language['Next']}</a></td>
        </c:if></li>
</ul>
</c:when>
<c:otherwise>
    <div class="text-center">
        <h2>${sessionScope.language['You_dont_have_orders_yet']}!</h2>
    </div>
</c:otherwise>
</c:choose>


<a href="${pageContext.request.contentType}/car/view/manager/manager.jsp">Go Back</a>
</body>
</html>
