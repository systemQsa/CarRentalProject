<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${sessionScope.language['Manager_page']}</title>
</head>
<body>
<h6>${sessionScope.userLogin}</h6>

<jsp:include page="/WEB-INF/view/include/header.jsp"/>

<%--navbar--%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${sessionScope.language['label.Rental_Car']}</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active">
                <a href="${pageContext.request.contextPath}/view/manager/manager.jsp">${sessionScope.language['Home']}</a></li>
            <li>
                <a href="?action=pagination&required=viewOrders&viewSuchOrders=approved&page=1&noOfRecords=5">${sessionScope.language['Approved_orders']}</a></li>
            <li>
                <a href="?action=pagination&required=viewOrders&viewSuchOrders=declined&page=1&noOfRecords=5">${sessionScope.language['Declined_orders']}</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li role="presentation"><a href="?lang=en">${sessionScope.language['header.lang.eng']}</a></li>
                    <li role="presentation"><a href="?lang=uk">${sessionScope.language['header.lang.ukr']}</a></li>
                </ul>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <div style="margin-top: 15%">
                <form method="get" action="${pageContext.request.contextPath}/helloServlet">
                    <input type="hidden" name="action" value="logout">
                    <button type="submit" class="btn btn-link">${sessionScope.language['label.Logout']}</button>
                </form>
            </div>
        </ul>
    </div>
</nav>

<%--shows errors msgs--%>
<div class="container">
    <div class="row">
        <c:if test="${not empty requestScope.err}">
            <div class="alert alert-danger alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <span class="text-center">
                        <strong>${sessionScope.language['error']}!</strong><br>
                         ${requestScope.errMSG}
                    </span>
            </div>
        </c:if>
    </div>
</div>

<%--displays orders list--%>
<c:choose>
<c:when test="${not empty applicationScope.orderList}">
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">${sessionScope.language['User_login']}</th>
            <th scope="col">${sessionScope.language['Passport']}</th>
            <th scope="col">${sessionScope.language['From_date']}</th>
            <th scope="col">${sessionScope.language['To_date']}</th>
            <th scope="col">${sessionScope.language['With_driver']}</th>
            <th scope="col">${sessionScope.language['Total_price']}</th>
            <th scope="col">${sessionScope.language['Feedback']}</th>
            <th scope="col">${sessionScope.language['Feedback']}</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="order" items="${applicationScope.orderList}">
        <tr>
            <td>${order.userLogin}</td>
            <td>${order.passport}</td>
            <td>${applicationScope.dateTimeFormatter.format(order.dateFrom)}</td>
            <td>${applicationScope.dateTimeFormatter.format(order.dateTo)}</td>
            <td>${order.withDriver}</td>
            <td>${order.receipt}</td>

            <td>
                <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                    <input type="hidden" name="action" value="acceptOrder">
                    <input type="hidden" name="acceptUserOrder" value="${order}">
                    <input type="hidden" name="approved" value="yes">
                    <input type="text" name="feedback">
                    <button type="submit" class="btn btn-primary btn-sm">${sessionScope.language['Accept_booking']}</button>
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                    <input type="hidden" name="action" value="declineOrder">
                    <input type="hidden" name="approved" value="no">
                    <input type="text" name="feedback">
                    <input type="hidden" name="declineUserOrder" value="${order}">
                    <button type="submit" class="btn btn-danger btn-sm">${sessionScope.language['Decline_booking']}</button>
                </form>
            </td>
            <td>
        </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p class="text-center">${sessionScope.language['There_are_no_records_yet']}!</p>
    </c:otherwise>
</c:choose>

</body>
</html>
