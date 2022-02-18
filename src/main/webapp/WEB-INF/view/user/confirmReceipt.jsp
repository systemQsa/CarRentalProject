<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>CONFIRM RECEIPT</h1>

<form method="post" action="${pageContext.request.contentType}/car/helloServlet">
    <div class="form-group">
        <label>Car Name</label>
        <p>${sessionScope.carNameReq}</p>

        <label>Car Class</label>
        <p>${sessionScope.carClassReq}</p>

        <label>Car Brand</label>
        <p>${sessionScope.carBrandReq}</p>

        <label>Car Rent Price</label>
        <p>${sessionScope.rentPriceReq}</p>

        <label>Passport</label>
        <p>${sessionScope.passport}</p>

        <label>With driver</label>
        <p>${sessionScope.withDriver}</p>

        <label>From Date</label>
        <p>${sessionScope.fromDate}</p>

        <label>To Date</label>
        <p>${sessionScope.toDate}</p>

        <label>Total</label>
        <p>${sessionScope.totalPrice}</p>

    </div>
    <button type="button">CONFIRM</button>
    <button type="button">CANCEL</button>
</form>

</body>
</html>
