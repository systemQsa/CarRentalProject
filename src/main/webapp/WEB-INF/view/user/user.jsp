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
<jsp:include page="/WEB-INF/view/include/header.jsp"/>
 <nav class="navbar fixed-top navbar-light bg-light">
     <h4 class="navbar-brand">WELCOME USER</h4>
   <p>${sessionScope.userLogin}</p><br><p>${sessionScope.language['my_balance']}</p><br>
    <h4 class="navbar-brand">${sessionScope.userBalance}</h4>

        <a class="navbar-brand" href="#">${sessionScope.language['label.Rental_Car']}</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
     <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
         <ul class="dropdown-menu" role="menu">
                 <li role="presentation"><a href="?lang=en">ENG</a></li>
                 <li role="presentation"><a href="?lang=uk">UKR</a></li>
          </ul>
     </li>
     <li>
         <form class="navbar-form navbar-left" target="_self">
              <div class="form-group">
                 <label class="control-label" for="search-field"><i
                         class="glyphicon glyphicon-search"></i></label>
                 <input class="form-control search-field" type="search" name="search" id="search-field">
             </div>
         </form>
     </li>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        ${sessionScope.language['sort_by']}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a href="?sort=byName&action=wantedOrder&order=sortCarsByName" role="button" class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_name']}</a>

                        <a href="?sort=byClass&action=wantedOrder&order=sortCarsByClass" role="button" class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_class']}</a>

                        <a href="?sort=byBrand&action=wantedOrder&order=sortCarsByBrand" role="button" class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_brand']}</a>

                        <a href="?sort=byPrice&action=wantedOrder&order=sortCarsByRentPrice" role="button" class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_price']}</a>


                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </li>
                <li class="nav-item">
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="action" value="topUpBalance">
                        <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                        <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">
                        <input id="ex" type="text" name="topUpBalance" style="width: 20%">
                        <button type="submit" class="btn btn-outline-warning my-2 my-sm-0 btn-sm">${sessionScope.language['top_up']}</button>
                    </form>
                </li>
                <li class="nav-item">
                  <a href="?action=pagination&required=viewMyOrders&page=1">${sessionScope.language['my_orders']}</a>
                </li>

            </ul>

            <form method="get" action="${pageContext.request.contextPath}/helloServlet" class="form-inline my-2 my-lg-0">
                <input type="hidden" name="action" value="logout">
                <button type="submit" class="btn btn-outline-secondary my-2 my-sm-0 btn-sm">${sessionScope.language['label.Logout']}</button>
            </form>
         </div>
</nav>



<%--<h4>TOP UP BALANCE</h4>--%>
<%--<p>${pageContext.session.servletContext.getAttribute("userName")}</p>--%>
<%--<p>${sessionScope.userIdByLogin}</p>--%>
<jsp:include page="/WEB-INF/view/allCars.jsp"/>

<%--<h3>${sessionScope.language['Cars']}</h3>--%>

<%--<table class="table table-hover">--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--        <th scope="col">${sessionScope.language['Id']}</th>--%>
<%--        <th scope="col">${sessionScope.language['Name']}</th>--%>
<%--        <th scope="col">${sessionScope.language['Class']}</th>--%>
<%--        <th scope="col">${sessionScope.language['Brand']}</th>--%>
<%--        <th scope="col">${sessionScope.language['Rental_Price']}</th>--%>
<%--    </tr>--%>
<%--    </thead>--%>
<%--    <tbody>--%>

<%--    <c:forEach var="car" items="${requestScope.allCars}">--%>
<%--        <tr>--%>
<%--            <th scope="row">${car.carId}</th>--%>
<%--            <td>${car.name}</td>--%>
<%--            <td>${car.carClass}</td>--%>
<%--            <td>${car.brand}</td>--%>
<%--            <td>${car.rentalPrice}</td>--%>
<%--            <td>--%>
<%--                <form method="post" action="${pageContext.request.contentType}/car/helloServlet">--%>
<%--                    <input type="hidden" name="action" value="bookCarReq">--%>
<%--                    <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">--%>
<%--                    <input type="hidden" name="carId" value="${car.carId}">--%>
<%--                    <input type="hidden" name="carName" value="${car.name}">--%>
<%--                    <input type="hidden" name="carClass" value="${car.carClass}">--%>
<%--                    <input type="hidden" name="carBrand" value="${car.brand}">--%>
<%--                    <input type="hidden" name="rentPrice" value="${car.rentalPrice}">--%>
<%--                    <button type="submit" class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>--%>
<%--                </form>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--    </tbody>--%>
<%--</table>--%>





<%--<c:choose>--%>
<%--    <c:when test="${requestScope.searchedCar.carId == 0}">--%>
<%--        <h3 class="text-center">Nothing was found!</h3>--%>
<%--    </c:when>--%>
<%--    <c:when test="${not empty requestScope.searchedCar}">--%>
<%--        <table class="table table-hover">--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <th scope="col">${sessionScope.language['Id']}</th>--%>
<%--                <th scope="col">${sessionScope.language['Name']}</th>--%>
<%--                <th scope="col">${sessionScope.language['Class']}</th>--%>
<%--                <th scope="col">${sessionScope.language['Brand']}</th>--%>
<%--                <th scope="col">${sessionScope.language['Rental_Price']}</th>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--            <tr>--%>
<%--                <td>${requestScope.searchedCar.carId}</td>--%>
<%--                <td>${requestScope.searchedCar.name}</td>--%>
<%--                <td>${requestScope.searchedCar.carClass}</td>--%>
<%--                <td>${requestScope.searchedCar.brand}</td>--%>
<%--                <td>${requestScope.searchedCar.rentalPrice}</td>--%>
<%--                <td>--%>
<%--                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">--%>
<%--                        <input type="hidden" name="action" value="bookCarReq">--%>
<%--                        <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">--%>
<%--                        <input type="hidden" name="carId" value="${requestScope.searchedCar.carId}">--%>
<%--                        <input type="hidden" name="carName" value="${requestScope.searchedCar.name}">--%>
<%--                        <input type="hidden" name="carClass" value="${requestScope.searchedCar.carClass}">--%>
<%--                        <input type="hidden" name="carBrand" value="${requestScope.searchedCar.brand}">--%>
<%--                        <input type="hidden" name="rentPrice" value="${requestScope.searchedCar.rentalPrice}">--%>
<%--                        <button type="submit" class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </c:when>--%>
<%--</c:choose>--%>



</body>
</html>
