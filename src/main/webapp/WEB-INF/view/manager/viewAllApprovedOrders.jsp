<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${sessionScope.language['View_all_approved_orders']}</title>
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

<c:if test="${requestScope.currentPage == 1}">
    <div class="btn-group dropright" aria-labelledby="dropdownMenuButton">
        <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Number of records
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=${requestScope.currentPage}&noOfRecords=5" role="button">5</a>

            <a class="dropdown-item" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=${requestScope.currentPage}&noOfRecords=10" role="button">10</a>

            <a class="dropdown-item" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=${requestScope.currentPage}&noOfRecords=25" role="button">25</a>

            <a class="dropdown-item" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=${requestScope.currentPage}&noOfRecords=50" role="button">50</a>
        </div>
    </div>
</c:if>

<c:choose>
    <c:when test="${not empty requestScope.listOrders}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">${sessionScope.language['label.Login']}</th>
                <th scope="col">${sessionScope.language['Passport']}</th>
                <th scope="col">${sessionScope.language['Receipt']}</th>
                <th scope="col">${sessionScope.language['From_date']}</th>
                <th scope="col">${sessionScope.language['To_date']}</th>
                <th scope="col">${sessionScope.language['With_driver']}</th>
                <th scope="col">${sessionScope.language['Car']}</th>
                <th scope="col">${sessionScope.language['Class']}</th>
                <th scope="col">${sessionScope.language['Brand']}</th>
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

<%--<c:if test="${requestScope.currentPage != 1}">--%>
<%--    <td><a href="?action=pagination&required=viewOrders&viewSuchOrders=approved&page=${requestScope.currentPage - 1}">${sessionScope.language['Previous']}</a></td>--%>
<%--</c:if>--%>
<%--<tr>--%>
<%--    <c:forEach begin="1" end="${requestScope.amountOfRecords.get()}" var="i">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${requestScope.currentPage eq i}">--%>
<%--                <td>${i}</td>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <td><a href="?action=pagination&required=viewOrders&viewSuchOrders=approved&page=${i}">${i}</a></td>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </c:forEach>--%>
<%--</tr>--%>
<%--<c:if test="${requestScope.currentPage lt requestScope.amountOfRecords.get()}">--%>
<%--    <td><a href="?action=pagination&required=viewOrders&viewSuchOrders=${pageContext.request.getParameter("viewSuchOrders")}&page=${requestScope.currentPage + 1}">${sessionScope.language['Next']}</a></td>--%>
<%--</c:if>--%>

<%--pagination--%>
<input type="hidden" name="action" value="pagination">
       <c:if test="${requestScope.noOfRecords <= requestScope.amountOfRecordsTotal}">
        <h3>
            <c:out value="${requestScope.noOfRecords}"/>
            <c:out value="${requestScope.amountOfRecordsTotal}"/>
        </h3>
            <div class="text-center">
                <ul class="pagination justify-content-center">

                    <li class="page-item"><c:if test="${requestScope.currentPage != 1}">
                        <td><a  class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=${requestScope.currentPage - 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Previous']}</a></td>
                    </c:if></li>
                    <li class="page-item"><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=1&noOfRecords=${requestScope.noOfRecords}">1</a></li>

                    <li class="page-item"><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=2&noOfRecords=${requestScope.noOfRecords}">2</a></li>

                    <li class="page-item"><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=3&noOfRecords=${requestScope.noOfRecords}">3</a></li>

                    <li class="page-item">
                        <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                            <td><a class="page-link" href="?action=pagination&required=viewOrders&viewSuchOrders=${requestScope.viewSuchOrders}&page=${requestScope.currentPage + 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Next']}</a></td>
                        </c:if></li>

                </ul>
            </div>
        </c:if>
</c:when>
<c:otherwise>
    <div class="text-center">
        <h2>${sessionScope.language['You_dont_have_orders_yet']}!</h2>
    </div>
</c:otherwise>
</c:choose>

 <a href="${pageContext.request.contextPath}/view/manager/manager.jsp">${sessionScope.language['Go_back']}</a>
</body>
</html>
