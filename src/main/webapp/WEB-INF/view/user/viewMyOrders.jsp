<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${sessionScope.language['my_orders']}</title>
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

<c:if test="${requestScope.err == 9}">
    <p class="text-center">${requestScope.errMSG}</p>
</c:if>


<div class="container">
<%-- displays all user personal orders --%>

    <div>
        <c:choose>
            <c:when test="${not empty requestScope.myPersonalOrders}">
                <div class="row">
                    <div class="col-md-6 col-sm-offset-10">
                        <a href="${pageContext.request.contextPath}/view/user/user.jsp"
                           role="button" class="btn btn-info btn-sm">${sessionScope.language['Go_back']}</a>
                    </div>
                </div>
                <table class="table table-hover">
                    <c:if test="${requestScope.currentPage == 1}">

                        <ul class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"
                               href="#">${sessionScope.language['Amount']}<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li>
                                    <a class="dropdown-item"
                                       href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=5"
                                       role="button">5</a>
                                </li>
                                <li>
                                    <a class="dropdown-item"
                                       href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=10"
                                       role="button">10</a>
                                </li>
                                <li>
                                    <a class="dropdown-item"
                                       href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=25"
                                       role="button">25</a>
                                </li>
                                <li>
                                    <a class="dropdown-item"
                                       href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage}&noOfRecords=50"
                                       role="button">50</a>
                                </li>
                            </ul>
                        </ul>
                    </c:if>

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
                            <td>${applicationScope.dateTimeFormatter.format(listOrders.order.dateFrom)}</td>
                            <td>${applicationScope.dateTimeFormatter.format(listOrders.order.dateTo)}</td>
                            <c:choose>
                                <c:when test="${listOrders.order.withDriver eq 'N'}">
                                    <td>${sessionScope.language['no']}</td>
                                </c:when>
                                <c:when test="${listOrders.order.withDriver eq 'Y'}">
                                    <td>${sessionScope.language['yes']}</td>
                                </c:when>
                            </c:choose>
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

                <%--pagination--%>
                <div class="text-center">
                    <input type="hidden" name="action" value="pagination">
                        <ul class="pagination justify-content-center">
                            <li class="page-item"><c:if test="${requestScope.currentPage != 1}">
                                <td><a  class="page-link"
                                        href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage - 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Previous']}</a></td>
                            </c:if></li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="?action=pagination&required=viewMyOrders&page=1&noOfRecords=${requestScope.noOfRecords}">1</a></li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="?action=pagination&required=viewMyOrders&page=2&noOfRecords=${requestScope.noOfRecords}">2</a></li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="?action=pagination&required=viewMyOrders&page=3&noOfRecords=${requestScope.noOfRecords}">3</a></li>
                            <li class="page-item">
                                <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                    <td><a class="page-link"
                                           href="?action=pagination&required=viewMyOrders&page=${requestScope.currentPage + 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Next']}</a></td>
                                </c:if></li>
                        </ul>
                </div>
            </c:when>
            <c:otherwise>
                <div class="h-100 row align-items-center">
                     <div class="col">
                        <div class="text-center">
                           <h2>${sessionScope.language['nothing_was_found']}!</h2>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
