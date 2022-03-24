<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${sessionScope.language['my_orders']}</title>
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

<div>
    <a href="${pageContext.request.contextPath}/view/user/user.jsp" class="btn btn-info" role="button">${sessionScope.language['Go_back']}</a>
</div>

<c:if test="${requestScope.err == 9}">
    <p class="text-center">${requestScope.errMSG}</p>
</c:if>
<c:if test="${requestScope.currentPage == 1}">
     <div class="btn-group dropright" aria-labelledby="dropdownMenuButton">
        <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Number of records
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=5" role="button">5</a>
            <a class="dropdown-item" href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=10" role="button">10</a>
            <a class="dropdown-item" href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=25" role="button">25</a>
            <a class="dropdown-item" href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=50" role="button">50</a>
        </div>
    </div>
</c:if>


<div class="container">
<%-- displays all user personal orders --%>

    <div class="row col-md-9 col-md-push-2">
        <c:choose>
            <c:when test="${not empty requestScope.myPersonalOrders}">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">${sessionScope.language['Passport']}</th>
                        <th scope="col">${sessionScope.language['From_date']}</th>
                        <th scope="col">${sessionScope.language['To_date']}</th>
                        <th scope="col">${sessionScope.language['With_driver']}</th>
                        <th scope="col">${sessionScope.language['Receipt']}</th>
                        <th scope="col">${sessionScope.language['Car']}</th>
                        <th scope="col">${sessionScope.language['Class']}</th>
                        <th scope="col">${sessionScope.language['Brand']}</th>
                        <th scope="col">${sessionScope.language['Feedback']}</th>
                        <th scope="col">${sessionScope.language['Approved']}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="listOrders" items="${requestScope.myPersonalOrders}">

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
                            <c:choose>
                                <c:when test="${listOrders.approved eq 'yes'}">
                                    <td>${sessionScope.language['yes']}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${sessionScope.language['no']}</td>
                                </c:otherwise>
                            </c:choose>
                          </tr>
                    </c:forEach>
                    </tbody>
                </table>

    <%--            <c:if test="${requestScope.currentPage != 1}">--%>
    <%--                <td><a href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage - 1}">${sessionScope.language['Previous']}</a></td>--%>
    <%--            </c:if>--%>
    <%--            <tr>--%>
    <%--                <c:forEach begin="1" end="${requestScope.amountOfRecords.get()}" var="i">--%>
    <%--                    <c:choose>--%>
    <%--                        <c:when test="${requestScope.currentPage eq i}">--%>
    <%--                            <td>${i}</td>--%>
    <%--                        </c:when>--%>
    <%--                        <c:otherwise>--%>
    <%--                            <td><a href="?action=pagination&required=viewMyOrders&page=${i}">${i}</a></td>--%>
    <%--                        </c:otherwise>--%>
    <%--                    </c:choose>--%>
    <%--                </c:forEach>--%>
    <%--            </tr>--%>

    <%--            <c:if test="${requestScope.currentPage lt requestScope.amountOfRecords.get()}">--%>
    <%--                <td><a href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage + 1}">${sessionScope.language['Next']}</a></td>--%>
    <%--            </c:if>--%>

                <%--pagination--%>
                <div class="text-center">
                    <input type="hidden" name="action" value="pagination">
<%--                    <c:if test="${requestScope.noOfRecords <= requestScope.amountOfRecordsTotal}">--%>
                        <ul class="pagination justify-content-center">
                            <li class="page-item"><c:if test="${requestScope.currentPage != 1}">
                                <td><a  class="page-link" href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage - 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Previous']}</a></td>
                            </c:if></li>
                            <li class="page-item"><a class="page-link" href="?action=pagination&required=viewMyOrders&page=1&noOfRecords=${requestScope.noOfRecords}">1</a></li>
                            <li class="page-item"><a class="page-link" href="?action=pagination&required=viewMyOrders&page=2&noOfRecords=${requestScope.noOfRecords}">2</a></li>
                            <li class="page-item"><a class="page-link" href="?action=pagination&required=viewMyOrders&page=3&noOfRecords=${requestScope.noOfRecords}">3</a></li>
                            <li class="page-item">
                                <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                    <td><a class="page-link" href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage + 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Next']}</a></td>
                                </c:if></li>
                        </ul>
<%--                    </c:if>--%>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center">
                    <h4>Empty here!</h4>
<%--                    <h2>${sessionScope.language['You_dont_have_orders_yet']}!</h2>--%>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
