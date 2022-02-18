<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <%--        <link rel="stylesheet" href="${pageContext.request.contentType}/css/index2.css">--%>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/index2.css'/>">
</head>
<body>
<div>
    <div class="header-blue">
        <nav class="navbar navbar-default navigation-clean-search">
            <div class="container">
                <div class="navbar-header"><a class="navbar-brand navbar-link" href="#">${language['label.WELCOME']} Admin</a>
                    <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
                </div>
                <div class="collapse navbar-collapse" id="navcol-1">
                    <ul class="nav navbar-nav">
                        <li><a href="${pageContext.request.contextPath}/index.jsp">
                            <p>${language['header.Home']}</p>
                        </a></li>
                        <li><a href="#"><p>${language['header.fleet']}</p></a></li>
                        <li><a href="#"> <p>${language['header.contact_us']}</p></a>
                        <li><a href="#"><p>${language['header.services_upper']}</p></a></li>
                        <li><a href="#"><p>${language['header.Make_a_booking']}</p></a></li>

                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${language['label.Language']}<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li role="presentation"><a href="?lang=en">ENG</a></li>
                                <li role="presentation"><a href="?lang=uk">UKR</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-left" target="_self">
                        <div class="form-group">
                            <label class="control-label" for="search-field"><i class="glyphicon glyphicon-search"></i></label>
                            <input class="form-control search-field" type="search" name="search" id="search-field">
                        </div>
                    </form>
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="action" value="logout">
                        <button type="submit" class="btn btn-info">${language['label.Logout']}</button>
                    </form>
                </div>
            </div>
        </nav>
    </div>
</div>

<div style="display: flex">
    <form method="get" action="${pageContext.request.contextPath}/helloServlet">
        <input type="hidden" name="action" value="findAllUsers">
        <button type="submit" class="btn btn-primary">SHOW ALL USERS</button>
    </form>
    <form method="get" action="${pageContext.request.contextPath}/helloServlet">
        <input type="hidden" name="action" value="findAllCars">
        <button type="submit" class="btn btn-primary">SHOW ALL CARS</button>
    </form>

    <a href="/car/view/admin/addNewCar.jsp" class="addCar">ADD CAR</a>
</div>
<p>user login</p>
<p>${pageContext.session.servletContext.getAttribute("userName")}</p>
<p>Users</p>
<c:if test="${not empty requestScope.allUsers}">
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">NAME</th>
        <th scope="col">SURNAME</th>
        <th scope="col">LOGIN</th>
        <th scope="col">PHONE</th>
        <th scope="col">BANNED</th>
        <th scope="col">REGISTER DATE</th>
        <th scope="col">ROLE</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.allUsers}">
        <tr>
            <th scope="row">${user.userId}</th>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.login}</td>
            <td>${user.phone}</td>
            <td>${user.isBanned}</td>
            <td>${user.registerDate}</td>
            <td>${user.userRole}</td>
            <td>
            <form method="post" action="${pageContext.request.contentType}/car/helloServlet">
                <input type="hidden" name="userLogin" value="${user.login}">
                <button type="submit" name="action" value="block" class="btn btn-danger btn-sm" >Block</button>
                <button type="submit" name="action" value="unblock" class="btn btn-success btn-sm">Unblock</button>
                <button type="submit" name="action" value="setManager"class="btn btn-primary btn-sm">Set Manager</button>
                <button type="submit" name="action" value="unsetManager" class="btn btn-secondary btn-sm">Unset manager</button>
            </form></td>
        </tr>

    </c:forEach>
    </tbody>
</table>

</c:if>

<%--<c:forEach var="user" items="${requestScope.allUsers}">--%>

<%--        <tr><td>${user.name}</td></tr>--%>
<%--        <tr><td>${user.surname}</td></tr>--%>
<%--        <tr><td>${user.login}</td>></tr>--%>
<%--        <tr><td>${user.phone}</td></tr>--%>
<%--        <tr><td>${user.registerDate}</td></tr>--%>
<%--        <tr><td>${user.isBanned}</td></tr>--%>
<%--        <form method="post" action="${pageContext.request.contentType}/helloServlet">--%>
<%--            <input type="hidden" name="userLogin" value="${user.login}">--%>
<%--            <input type="hidden" name="command" value="block">--%>
<%--            <button type="submit" name="action" value="blockUnblock">BLOCK</button>--%>
<%--            <input type="hidden" name="command" value="unblock">--%>
<%--            <button type="submit" name="action" value="blockUnblock">UNBLOCK</button>--%>
<%--            <button type="submit" name="action" value="setManager">SET MANAGER</button>--%>
<%--            <button type="submit" name="action" value="unsetManager">UNSET MANAGER</button>--%>
<%--        </form>--%>
<%--    <p>=================</p>--%>

<%--</c:forEach>--%>
<h3>========================================================</h3>

<c:if test="${not empty requestScope.allCars}">
    <p>Cars</p>
    <c:forEach var="car" items="${requestScope.allCars}">
        <tr><td>${car.carId}</td></tr>
        <tr><td>${car.name}</td></tr>
        <tr><td>${car.carClass}</td></tr>
        <tr><td>${car.brand}</td></tr>
        <tr><td>${car.rentalPrice}</td></tr>
        <form method="post" action="${pageContext.request.contextPath}/helloServlet">
            <input type="hidden" name="carId" value="${car.carId}">
            <button type="submit" name="action" value="getInfoCurrCar" class="btn btn-primary">UPDATE CAR</button>
            <button type="submit" name="action" value="deleteCar" class="btn btn-danger">DELETE CAR</button>
        </form>
        <p>=================</p>
    </c:forEach>
</c:if>



<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
