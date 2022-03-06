<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.langi=uage['Confirm_order']}</title>
</head>
<body>
<h1>CONFIRM RECEIPT</h1>
<h2> CAR ID ${sessionScope.carIdReq}</h2>

<p>${sessionScope.userLogin}</p>

<jsp:include page="${pageContext.request.contentType}/WEB-INF/view/include/header.jsp"/>

<%--<h1>CONFIRM RECEIPT</h1>--%>
<%--<h2> CAR ID ${sessionScope.carIdReq}</h2>--%>

<%--<p>${sessionScope.userLogin}</p>--%>


<div class="col-md-6 col-lg-6 offset-lg-3 offset-md-3 mt-4">
    <div class="bg-light p-5 border shadow">
        <%--                <h3>${pageContext.request.locale}</h3>--%>
        <!-- Login Form -->
        <form method="post" action="${pageContext.request.contextPath}/helloServlet">

            <input type="hidden" name="carId" value="${sessionScope.carIdReq}">
            <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">
            <input type="hidden" name="userBalance" value="${sessionScope.userBalance}">
            <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">

            <%--            todo display center text--%>
            <div>
                <h4 class="text-center">${sessionScope.language['Confirm_order']}</h4>
                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['Name']}</div>
                    <div class="col-md-8">${sessionScope.carNameReq}</div>
                </div>
                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['Class']}</div>
                    <div class="col-md-8">${sessionScope.carClassReq}</div>
                </div>

                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['Brand']}</div>
                    <div class="col-md-8"> ${sessionScope.carBrandReq}</div>
                </div>
                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['Rental_Price']}</div>
                    <input type="hidden" name="carRentPrice" value="${applicationScope.rentPriceReq}">
                    <div class="col-md-8"> ${applicationScope.rentPriceReq}</div>
                </div>
                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['Passport']}</div>
                    <input type="hidden" name="userPassport" value="${applicationScope.passport}">
                    <div class="col-md-8"> ${applicationScope.passport}</div>

                </div>
                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['With_driver']}</div>
                    <input type="hidden" name="withDriver" value="${applicationScope.withDriver}">
                    <div class="col-md-8">${applicationScope.withDriver}</div>
                </div>
                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['From_date']}</div>
                    <input type="hidden" name="fromDate" value="${applicationScope.fromDate}">
                    <div class="col-md-8">${applicationScope.fromDate}</div>
                </div>
                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['To_date']}</div>
                    <input type="hidden" name="toDate" value="${applicationScope.toDate}">
                    <div class="col-md-8">${applicationScope.toDate}</div>
                </div>

                <div class="mb-6 row">
                    <div class="col-md-4">${sessionScope.language['Total']}</div>
                    <input type="hidden" name="totalPrice" value="${applicationScope.totalPrice}">
                    <div class="col-md-8">${applicationScope.totalPrice}</div>
                </div>
            </div>
            <%--            <button type="submit" class="btn btn-primary w-100 my-3 shadow" >Confirm</button>--%>

            <c:choose>
                <c:when test="${not empty sessionScope.resultIfBalanceOk}">
                    <p>${sessionScope.language['Confirm_the_booking']}?</p>
                    <input type="hidden" name="action" value="confirmBooking">
                    <button type="submit" class="btn btn-primary w-100 my-3 shadow">${sessionScope.language['Confirm']}</button>
                </c:when>
                <c:when test="${requestScope.err == 11}">
                    <p>${requestScope.errMSG}</p>
                </c:when>
                <c:otherwise>
                    <p>${sessionScope.language['You_don`t_have_enough_money_for_booking']}! ${sessionScope.language['Please_pop_up_your_balance_and_try_again']}!</p>
                    <button type="button" class="btn btn-primary w-100 my-3 shadow">${sessionScope.language['Confirm']}</button>
                </c:otherwise>
            </c:choose>
        </form>
        <form method="post" action="${pageContext.request.contextPath}/helloServlet">
            <input type="hidden" name="userPassport" value="${sessionScope.passport}">
            <input type="hidden" name="fromDate" value="${sessionScope.fromDate}">
            <input type="hidden" name="toDate" value="${sessionScope.toDate}">
            <input type="hidden" name="withDriver" value="${sessionScope.withDriver}">
            <input type="hidden" name="totalPrice" value="${sessionScope.totalPrice}">
            <input type="hidden" name="action" value="cancelBooking">
            <button type="submit" class="btn btn-danger w-100 my-3 shadow">${sessionScope.language['Cancel']}</button>
        </form>
    </div>
</div>
</body>
</html>
