<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>CONFIRM RECEIPT</h1>
<h2> CAR ID ${sessionScope.carIdReq}</h2>

<form method="post" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="carId" value="${sessionScope.carIdReq}">
    <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">
    <input type="hidden" name="userBalance" value="${sessionScope.userBalance}">
    <div class="form-group">
        <label>Car Name</label>
        <p>${sessionScope.carNameReq}</p>

        <label>Car Class</label>
        <p>${sessionScope.carClassReq}</p>

        <label>Car Brand</label>
        <p>${sessionScope.carBrandReq}</p>

        <label>Car Rent Price</label>
        <input type="hidden" name="carRentPrice" value="${sessionScope.rentPriceReq}">
        <p>${sessionScope.rentPriceReq}</p>

        <label>Passport</label>
        <input type="hidden" name="userPassport" value="${sessionScope.passport}">
        <p>${sessionScope.passport}</p>

        <label>With driver</label>
        <input type="hidden" name="withDriver" value="${sessionScope.withDriver}">
        <p>${sessionScope.withDriver}</p>

        <label>From Date</label>
        <input type="hidden" name="fromDate" value="${sessionScope.fromDate}">
        <p>${sessionScope.fromDate}</p>

        <label>To Date</label>
        <input type="hidden" name="toDate" value="${sessionScope.toDate}">
        <p>${sessionScope.toDate}</p>

        <label>Total</label>
        <input type="hidden" name="totalPrice" value="${sessionScope.totalPrice}">
        <p>${sessionScope.totalPrice}</p>

                <c:choose>
                    <c:when test="${not empty sessionScope.resultIfBalanceOk}">
                        <p>Confirm the booking?</p>
                        <input type="hidden" name="action" value="confirmBooking">
                        <button type="submit" class="btn btn-primary btn-sm">CONFIRM</button>
                    </c:when>
                    <c:otherwise>
                        <p>You dont have enough money on balance for booking.Please pop up your balance and try again!</p>
                        <button type="button" class="btn btn-primary btn-sm">CONFIRM</button>
                    </c:otherwise>
                </c:choose>

    </div>
</form>
<form method="post" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="userPassport" value="${sessionScope.passport}">
    <input type="hidden" name="fromDate" value="${sessionScope.fromDate}">
    <input type="hidden" name="toDate" value="${sessionScope.toDate}">
    <input type="hidden" name="withDriver" value="${sessionScope.withDriver}">
    <input type="hidden" name="totalPrice" value="${sessionScope.totalPrice}">
    <input type="hidden" name="action" value="cancelBooking">
            <button type="submit">CANCEL</button>
</form>

</body>
</html>
