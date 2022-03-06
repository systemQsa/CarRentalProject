<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['Admin']}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <%--        <link rel="stylesheet" href="${pageContext.request.contentType}/css/index2.css">--%>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/index2.css'/>">
</head>
<body>
<div class="header-blue">
    <nav class="navbar navbar-default navigation-clean-search">
        <div class="container">
            <div class="navbar-header"><a class="navbar-brand navbar-link" href="#">${sessionScope.language['label.WELCOME']}
                Admin</a>
                <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span
                        class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
                        class="icon-bar"></span><span class="icon-bar"></span></button>
            </div>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav">
                    <li><a href="${pageContext.request.contextPath}/index.jsp">
                        <p>${sessionScope.language['header.Home']}</p>
                    </a></li>

                     <li>
                        <a>

                            <p>
                            <form method="get" action="${pageContext.request.contextPath}/helloServlet">
                                <input type="hidden" name="action" value="findAllUsers">
                                <button type="submit" class="buttonToLink">${sessionScope.language['All_users']}</button>
                            </form>
                            <%--                                   <form method="get" action="${pageContext.request.session.servletContext.contextPath}/view/admin/admin.jsp/helloServlet">--%>
                            <%--                                 <input type="hidden" name="action" value="findAllUsers">--%>
                            <%--                                        <button type="submit" class="buttonToLink">All users</button>--%>
                            <%--                                </form>--%>


                            <%--                                    <a class="nav-link" href="${pageContext.request.contextPath}/view/admin/admin.jsp/helloServlet?action=findAllUsers">All users</a>--%>
                            </p>
                        </a>
                    </li>
                    <li>
                        <a>
                            <p>
                            <form method="get" action="${pageContext.request.contextPath}/helloServlet">
                                <input type="hidden" name="action" value="findAllCars">
                                <button type="submit" class="buttonToLink">${sessionScope.language['All_cars']}</button>
                            </form>
                            </p>
                        </a>
                    </li>
                    <li>

                        <a href="${pageContext.request.contextPath}/view/admin/addNewCar.jsp">
                            <p>${sessionScope.language['Add_car']}</p></a>

                    </li>
<%--                    <li>--%>
<%--                        <form method="get" action="admin.jsp/helloServlet">--%>
<%--                            <input type="hidden" name="action" value="findAllUsers">--%>
<%--                            <button type="submit">Users</button>--%>
<%--                        </form>--%>
<%--                     </li>--%>

                    <li><a href="?action=findAllUsers">${sessionScope.language['All_users']}</a></li>
                    <li>
                        <a href="?action=findAllCars">${sessionScope.language['All_cars']}</a>
                    </li>

                             <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                        <li role="presentation"><a href="?lang=en">ENG</a></li>

                                         <li role="presentation"><a href="?lang=uk">UKR</a></li>
                                 </ul>
                            </li>

                </ul>
                <form class="navbar-form navbar-left" target="_self">
<%--                    <input type="hidden" name="action" value="search">--%>
                    <div class="form-group">
                        <label class="control-label" for="search-field"><i
                                class="glyphicon glyphicon-search"></i></label>
                        <input class="form-control search-field" type="search" name="search" id="search-field">
                    </div>
                </form>
                <div class="text-right">
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="action" value="logout">
                        <button type="submit" class="btn btn-info">${sessionScope.language['label.Logout']}</button>
                    </form>
                </div>
            </div>
        </div>
    </nav>
</div>
</div>
 <p>${pageContext.session.servletContext.getAttribute("userName")}</p>


<c:if test="${not empty requestScope.allUsers}">
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">${sessionScope.language['Id']}</th>
            <th scope="col">${sessionScope.language['user.Name']}</th>
            <th scope="col">${sessionScope.language['user.Surname']}</th>
            <th scope="col">${sessionScope.language['user.Login']}</th>
            <th scope="col">${sessionScope.language['user.Phone']}</th>
            <th scope="col">${sessionScope.language['user.Banned']}</th>
            <th scope="col">${sessionScope.language['user.Register_date']}</th>
            <th scope="col">${sessionScope.language['user.Role']}</th>
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
                <c:choose>
                    <c:when test="${user.isBanned eq 'Y'}">
                        <td>${sessionScope.language['yes']}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${sessionScope.language['no']}</td>
                    </c:otherwise>
                </c:choose>
                <td>${user.registerDate}</td>
                <c:choose>
                    <c:when test="${user.userRole eq 'admin'}">
                        <td>${sessionScope.language['admin']}</td>
                    </c:when>
                    <c:when test="${user.userRole eq 'user'}">
                        <td>${sessionScope.language['user']}</td>
                    </c:when>
                    <c:when test="${user.userRole eq 'manager'}">
                        <td>${sessionScope.language['manager']}</td>
                    </c:when>
                </c:choose>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="userId" value="${user.userId}">
                        <input type="hidden" name="userLogin" value="${user.login}">
                        <button type="submit" name="action" value="block" class="btn btn-danger btn-sm">Block</button>
                        <button type="submit" name="action" value="unblock" class="btn btn-success btn-sm">Unblock</button>
                        <button type="submit" name="action" value="setManager" class="btn btn-primary btn-sm">Set Manager
                        </button>
                        <button type="submit" name="action" value="unsetManager" class="btn btn-secondary btn-sm">Unset Manager
                        </button>
                    </form>
                </td>
            </tr>

        </c:forEach>
        </tbody>
        <a href="${pageContext.request.contextPath}/view/admin/admin.jsp">${sessionScope.language['Go_back']}</a>
    </table>

</c:if>

<c:if test="${not empty requestScope.allCars}">
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">${sessionScope.language['Id']}</th>
            <th scope="col">${sessionScope.language['Name']}</th>
            <th scope="col">${sessionScope.language['Class']}</th>
            <th scope="col">${sessionScope.language['Brand']}</th>
            <th scope="col">${sessionScope.language['Rental_Price']}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="car" items="${requestScope.allCars}">
            <tr>
                <td>${car.carId}</td>
                <td>${car.name}</td>
                <td>${car.carClass}</td>
                <td>${car.brand}</td>
                <td>${car.rentalPrice}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="carId" value="${car.carId}">
                        <button type="submit" name="action" value="getInfoCurrCar" class="btn btn-primary btn-sm">${sessionScope.language['Update']}
                        </button>
                        <button type="submit" name="action" value="deleteCar" class="btn btn-danger btn-sm">${sessionScope.language['Delete']}
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        <a href="${pageContext.request.contextPath}/view/admin/admin.jsp">${sessionScope.language['Go_back']}</a>
    </table>
</c:if>
<div class="text-center">
    <form method="get">
        <input type="hidden" name="action" value="pagination">
        <ul class="pagination justify-content-center">
            <li class="page-item"><a class="page-link" href="#">Previous</a></li>
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.request.contentType}/car/helloServlet?action=pagination&page=1">1</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">Next</a></li>
        </ul>
    </form>
</div>

<c:choose>
    <c:when test="${requestScope.searchedUser.userId == 0}">
      <h3 class="text-center">Nothing was found!</h3>
    </c:when>
    <c:when test="${ not empty requestScope.searchedUser}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">${sessionScope.language['Id']}</th>
                <th scope="col">${sessionScope.language['user.Name']}</th>
                <th scope="col">${sessionScope.language['user.Surname']}</th>
                <th scope="col">${sessionScope.language['user.Login']}</th>
                <th scope="col">${sessionScope.language['user.Phone']}</th>
                <th scope="col">${sessionScope.language['user.Banned']}</th>
                <th scope="col">${sessionScope.language['user.Register_date']}</th>
                <th scope="col">${sessionScope.language['user.Role']}</th>

            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${requestScope.searchedUser.userId}</td>
                <td>${requestScope.searchedUser.name}</td>
                <td>${requestScope.searchedUser.surname}</td>
                <td>${requestScope.searchedUser.login}</td>
                <td>${requestScope.searchedUser.phone}</td>
                <c:choose>
                    <c:when test="${requestScope.searchedUser.isBanned eq 'Y'}">
                        <td>${sessionScope.language['yes']}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${sessionScope.language['no']}</td>
                    </c:otherwise>
                </c:choose>
                <td>${requestScope.searchedUser.registerDate}</td>

                <c:choose>
                    <c:when test="${requestScope.searchedUser.role eq '1'}">
                        <td>${sessionScope.language['admin']}</td>
                    </c:when>
                    <c:when test="${requestScope.searchedUser.role eq '2'}">
                        <td>${sessionScope.language['user']}</td>
                    </c:when>
                    <c:when test="${requestScope.searchedUser.role eq '3'}">
                        <td>${sessionScope.language['manager']}</td>
                    </c:when>
                </c:choose>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="userId" value="${requestScope.searchedUser.userId}">
                        <input type="hidden" name="userLogin" value="${requestScope.searchedUser.login}">
                        <button type="submit" name="action" value="block" class="btn btn-danger btn-sm">Block</button>
                        <button type="submit" name="action" value="unblock" class="btn btn-success btn-sm">Unblock</button>
                        <button type="submit" name="action" value="setManager" class="btn btn-primary btn-sm">Set Manager
                        </button>
                        <button type="submit" name="action" value="unsetManager" class="btn btn-secondary btn-sm">Unset Manager
                        </button>
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
    </c:when>
</c:choose>


<c:choose>
    <c:when test="${requestScope.searchedCar.carId == 0}">
        <h3 class="text-center">Nothing was found!</h3>
    </c:when>
    <c:when test="${not empty requestScope.searchedCar}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">${sessionScope.language['Id']}</th>
                <th scope="col">${sessionScope.language['Name']}</th>
                <th scope="col">${sessionScope.language['Class']}</th>
                <th scope="col">${sessionScope.language['Brand']}</th>
                <th scope="col">${sessionScope.language['Rental_Price']}</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${requestScope.searchedCar.carId}</td>
                <td>${requestScope.searchedCar.name}</td>
                <td>${requestScope.searchedCar.carClass}</td>
                <td>${requestScope.searchedCar.brand}</td>
                <td>${requestScope.searchedCar.rentalPrice}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                        <input type="hidden" name="carId" value="${requestScope.searchedCar.carId}">
                        <button type="submit" name="action" value="getInfoCurrCar" class="btn btn-primary btn-sm">${sessionScope.language['Update']}
                        </button>
                        <button type="submit" name="action" value="deleteCar" class="btn btn-danger btn-sm">${sessionScope.language['Delete']}
                        </button>
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
    </c:when>
</c:choose>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
