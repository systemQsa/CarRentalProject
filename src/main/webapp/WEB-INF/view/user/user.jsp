<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>USER</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<h1>WELCOME USER</h1>
<form method="get" action="${pageContext.request.contextPath}/helloServlet">
    <input type="hidden" name="action" value="logout">
    <button type="submit"> ${language['label.Logout']}</button>
</form>
<h4>TOP UP BALANCE</h4>
<p>${pageContext.session.servletContext.getAttribute("userName")}</p>
<p>User Balance</p>
<h4>${sessionScope.userBalance}</h4>
<form method="post" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="topUpBalance">
    <input type="hidden" name="userLogin" value="${pageContext.session.servletContext.getAttribute("userName")}">
    <input type="text" name="topUpBalance">
    <button type="submit">TOP UP</button>
</form>
<h3>CARS</h3>
<div style="display: flex">
<form method="get" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="wantedOrder">
    <input type="hidden" name="order" value="sortCarsByName">
    <button type="submit">SORT BY NAME</button>
</form>
<form method="get" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="wantedOrder">
    <input type="hidden" name="order" value="sortCarsByClass">
    <button type="submit"> SORT BY CLASS</button>
</form>
<form method="get" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="wantedOrder">
    <input type="hidden" name="order" value="sortCarsByBrand">
    <button type="submit"> SORT BY BRAND</button>
</form>
<form method="get" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="wantedOrder">
    <input type="hidden" name="order" value="sortCarsByRentPrice">
    <button type="submit"> SORT BY PRICE</button>
</form>
</div>

<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">NAME</th>
        <th scope="col">CLASS</th>
        <th scope="col">BRAND</th>
        <th scope="col">RENTAL PRICE</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="car" items="${requestScope.allCars}">
        <tr>
            <th scope="row">${car.carId}</th>
            <td>${car.name}</td>
            <td>${car.carClass}</td>
            <td>${car.brand}</td>
            <td>${car.rentalPrice}</td>
            <td>
            <form method="post" action="${pageContext.request.contentType}/car/helloServlet">
                <input type="hidden" name="action" value="bookCarReq">
                <input type="hidden" name="carId" value="${car.carId}">
                <input type="hidden" name="carName" value="${car.name}">
                <input type="hidden" name="carClass" value="${car.carClass}">
                <input type="hidden" name="carBrand" value="${car.brand}">
                <input type="hidden" name="rentPrice" value="${car.rentalPrice}">
                <button type="submit" class="btn btn-warning btn-sm">Book Car</button>
            </form></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
