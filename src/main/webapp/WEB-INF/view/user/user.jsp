<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="resources" var="locale"/>
<html>
<head>
    <title>USER</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
 <nav class="navbar fixed-top navbar-light bg-light">
     <h4 class="navbar-brand">WELCOME USER</h4>
   <p>${sessionScope.userLogin}</p><br><p>User Balance</p><br>
    <h4 class="navbar-brand">${sessionScope.userBalance}</h4>

        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Sort By
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <form method="get" action="${pageContext.request.contentType}/car/helloServlet"
                              class="dropdown-item">
                            <input type="hidden" name="action" value="wantedOrder">
                            <input type="hidden" name="order" value="sortCarsByName">
                            <button type="submit" class="btn btn-outline-primary my-2 my-sm-0 btn-sm">SORT BY NAME</button>
                        </form>
                        <form method="get" action="${pageContext.request.contentType}/car/helloServlet"
                              class="dropdown-item">
                            <input type="hidden" name="action" value="wantedOrder">
                            <input type="hidden" name="order" value="sortCarsByClass">
                            <button type="submit" class="btn btn-outline-primary my-2 my-sm-0 btn-sm"> SORT BY CLASS
                            </button>
                        </form>
                        <form method="get" action="${pageContext.request.contentType}/car/helloServlet"
                              class="dropdown-item">
                            <input type="hidden" name="action" value="wantedOrder">
                            <input type="hidden" name="order" value="sortCarsByBrand">
                            <button type="submit" class="btn btn-outline-primary my-2 my-sm-0 btn-sm"> SORT BY BRAND
                            </button>
                        </form>
                        <form method="get" action="${pageContext.request.contentType}/car/helloServlet"
                              class="dropdown-item">
                            <input type="hidden" name="action" value="wantedOrder">
                            <input type="hidden" name="order" value="sortCarsByRentPrice">
                            <button type="submit" class="btn btn-outline-primary my-2 my-sm-0 btn-sm"> SORT BY PRICE
                            </button>
                        </form>

                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </li>
                <li class="nav-item">
                    <form method="post" action="${pageContext.request.contentType}/car/helloServlet">
                        <input type="hidden" name="action" value="topUpBalance">
                        <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                        <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">
                        <input id="ex" type="text" name="topUpBalance" style="width: 20%">
                        <button type="submit" class="btn btn-outline-warning my-2 my-sm-0 btn-sm">TOP UP</button>
                    </form>
                </li>
                <li class="nav-item">
                    <form method="get" action="${pageContext.request.contentType}/car/helloServlet">
                        <input type="hidden" name="action" value="myOrders">
                        <button type="submit" class="btn btn-outline-primary my-2 my-sm-0 btn-sm">My Orders</button>
                    </form>
                </li>


            </ul>
            <form method="get" action="${pageContext.request.contextPath}/helloServlet" class="form-inline my-2 my-lg-0">
                <input type="hidden" name="action" value="logout">
                <button type="submit" class="btn btn-outline-secondary my-2 my-sm-0 btn-sm"><fmt:message bundle="${locale}"
                                                                                                         key="label.Logout"/></button>
            </form>
            <%--        <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">--%>
            <%--            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>--%>

        </div>
</nav>

<nav class="navbar navbar-expand-lg navbar-light bg-light">


</nav>


<%--<h4>TOP UP BALANCE</h4>--%>
<%--<p>${pageContext.session.servletContext.getAttribute("userName")}</p>--%>
<%--<p>${sessionScope.userIdByLogin}</p>--%>

<h3>CARS</h3>

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
                    <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                    <input type="hidden" name="carId" value="${car.carId}">
                    <input type="hidden" name="carName" value="${car.name}">
                    <input type="hidden" name="carClass" value="${car.carClass}">
                    <input type="hidden" name="carBrand" value="${car.brand}">
                    <input type="hidden" name="rentPrice" value="${car.rentalPrice}">
                    <button type="submit" class="btn btn-warning btn-sm">Book Car</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="/WEB-INF/view/include/header.jsp"/>
</body>
</html>
