<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="resources" var="locale"/>

<html>
<head>
    <title>${sessionScope.language['Manager_page']}</title>
</head>
<body>
<h6>${sessionScope.userLogin}</h6>
<jsp:include page="${pageContext.request.contentType}/WEB-INF/view/include/header.jsp"/>

<%--<p class="d-inline-flex p-2">${sessionScope.userLogin}</p>--%>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01"
            aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse d-flex justify-content-around" id="navbarTogglerDemo01">
        <a class="navbar-brand" href="#">Rental car</a>
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
<%--                <form method="get" action="${pageContext.request.contextPath}/helloServlet">--%>
<%--                    <input type="hidden" name="action" value="viewOrders">--%>
<%--                    <input type="hidden" name="viewSuchOrders" value="approved">--%>
<%--                    <button class="btn btn-outline-primary my-2 my-sm-0 btn-sm" type="submit">${sessionScope.language['Approved_orders']}</button>--%>
<%--                </form>--%>
                <a href="?action=pagination&required=viewOrders&viewSuchOrders=approved&page=1">${sessionScope.language['Approved_orders']}</a>
            </li>
            <li class="nav-item">
                <a href="?action=pagination&required=viewOrders&viewSuchOrders=declined&page=1">${sessionScope.language['Declined_orders']}</a>
            </li>
            <li class="nav-item">
                <form method="get" action="${pageContext.request.contextPath}/helloServlet">
                    <input type="hidden" name="action" value="viewOrders">
                    <input type="hidden" name="viewSuchOrders" value="declined">
                    <button class="btn btn-outline-primary my-2 my-sm-0 btn-sm" type="submit">${sessionScope.language['Declined_orders']}</button>
                </form>
            </li>
            <li class="nav-item">
                <div class="dropdown show">
<%--                    <button type="button" class="btn btn-outline-warning my-2 my-sm-0 btn-sm dropdown-toggle"--%>
<%--                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--                       ${sessionScope.language['Language']}--%>
<%--                    </button>--%>
                    <div class="dropdown-menu">

                    </div></div>

            <li class="nav-item">
                <form method="get" action="${pageContext.request.contentType}/car/helloServlet">
                    <input type="hidden" name="action" value="viewAllUnAcceptedOrders">
                    <button type="submit" class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['View_orders']}</button>
                </form>
            </li>

            <li class="dropdown"><a class="dropdown-toggle btn btn-warning btn-sm" role="button" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">

                    <li role="presentation"><a href="?lang=en">ENG</a></li>
                    <li role="presentation"><a href="?lang=uk">UKR</a></li>
                </ul>
            </li>

          </ul>

        </ul>
        <form method="get" action="${pageContext.request.contextPath}/helloServlet">
            <input type="hidden" name="action" value="logout">
            <button class="btn btn-outline-secondary my-2 my-sm-0 btn-sm" type="submit">${sessionScope.language['label.Logout']}</button>
        </form>
    </div>
</nav>


<c:choose>
<c:when test="${not empty applicationScope.orderList}">
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">User login</th>
        <th scope="col">Passport</th>
        <th scope="col">From Date</th>
        <th scope="col">To Date</th>
        <th scope="col">With Driver</th>
        <th scope="col">Total Price</th>
        <th scope="col">Feedback</th>
        <th scope="col">Feedback</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="order" items="${applicationScope.orderList}">
    <tr>
        <td>${order.userLogin}</td>
        <td>${order.passport}</td>
        <td>${order.fromDate}</td>
        <td>${order.toDate}</td>
        <td>${order.withDriver}</td>
        <td>${order.receipt}</td>

        <td>
            <form method="post" action="${pageContext.request.contentType}/car/helloServlet">
                <input type="hidden" name="action" value="acceptOrder">
                <input type="hidden" name="acceptUserOrder" value="${order}">
                <input type="hidden" name="approved" value="yes">
                <input type="text" name="feedback">
                <button type="submit" class="btn btn-primary btn-sm">Accept order</button>
            </form>
        </td>
        <td>
            <form method="post" action="${pageContext.request.contentType}/car/helloServlet">
                <input type="hidden" name="action" value="declineOrder">
                <input type="hidden" name="approved" value="no">
                <input type="text" name="feedback">
                <input type="hidden" name="declineUserOrder" value="${order}">
                <button type="submit" class="btn btn-danger btn-sm">Decline Order</button>
            </form>
        </td>
        <td>
    </tr>
    </c:forEach>
    </c:when>
    <c:otherwise>
    <p>${sessionScope.language['There_are_no_records_yet']}!</p>
    </c:otherwise>
    </c:choose>














<%--<form method="get" action="${pageContext.request.contextPath}/helloServlet">--%>
<%--    <input type="hidden" name="action" value="logout">--%>
<%--    <button type="submit"> <fmt:message bundle="${locale}" key="label.Logout"/> </button>--%>
<%--</form>--%>
<%--<form method="get" action="${pageContext.request.contentType}/car/helloServlet">--%>
<%--    <input type="hidden" name="action" value="viewAllUnAcceptedOrders">--%>
<%--    <button type="submit">View Orders</button>--%>
<%--</form>--%>


<%--<form method="get" action="${pageContext.request.contentType}/car/helloServlet">--%>
<%--    <input type="hidden" name="action" value="viewOrders">--%>
<%--    <input type="hidden" name="viewSuchOrders" value="approved">--%>
<%--    <button type="submit">Approved Orders</button>--%>
<%--</form>--%>

<%--<form method="get" action="${pageContext.request.contentType}/car/helloServlet">--%>
<%--    <input type="hidden" name="action" value="viewOrders">--%>
<%--    <input type="hidden" name="viewSuchOrders" value="declined">--%>
<%--    <button type="submit">Declined Orders</button>--%>
<%--</form>--%>




</body>
</html>
