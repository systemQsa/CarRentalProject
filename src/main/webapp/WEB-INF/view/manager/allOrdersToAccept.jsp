<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['All_not_approved_bookings']}</title>
</head>
<jsp:include page="${pageContext.request.contentType}/WEB-INF/view/include/header.jsp"/>
<body>
<p>${requestScope.login}</p>
<c:choose>
    <c:when test="${applicationScope.orderList == null}">
             <p>${sessionScope.language['There_are_no_records_yet']}!</p>
     </c:when>
    <c:otherwise>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>${sessionScope.language['user.Login']}</th>
                <th>${sessionScope.language['Passport']}</th>
                <th>${sessionScope.language['From_date']}</th>
                <th>${sessionScope.language['To_date']}</th>
                <th>${sessionScope.language['With_driver']}</th>
                <th>${sessionScope.language['Rental_Price']}</th>
            </tr>
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

                    <td><form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="action" value="acceptOrder">
                        <input type="hidden" name="acceptUserOrder" value="${order}">
                        <input type="hidden" name="approved" value="yes">
                        <input type="text" name="feedback">
                        <button type="submit" class="btn btn-success btn-sm">${sessionScope.language['Accept_booking']}</button>
                    </form></td>
                    <td><form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="action" value="declineOrder">
                        <input type="hidden" name="approved" value="no">
                        <input type="text" name="feedback">
                        <input type="hidden" name="declineUserOrder" value="${order}">
                        <button type="submit" class="btn btn-danger btn-sm">${sessionScope.language['Decline_booking']}</button>
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
    </c:otherwise>
</c:choose>

<a href="${pageContext.request.contextPath}/view/manager/manager.jsp">${sessionScope.language['Go_back']}</a>
</body>
</html>
