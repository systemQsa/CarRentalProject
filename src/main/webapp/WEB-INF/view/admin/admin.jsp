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
            <link rel="stylesheet" href="${pageContext.request.contentType}/css/index2.css">
 </head>
<body>
 <p>${pageContext.session.servletContext.getAttribute("userName")}</p>


<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${sessionScope.language['label.WELCOME']}</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="?action=findAllUsers">${sessionScope.language['All_users']}</a></li>
            <li><a href="?action=findAllCars&page=1&noOfRecords=5">${sessionScope.language['All_cars']}</a></li>
            <li><a href="${pageContext.request.contextPath}/view/admin/addNewCar.jsp">${sessionScope.language['Add_car']}</a></li>
            <li class="dropdown"><a class="dropdown-toggle text-white" data-toggle="dropdown" aria-expanded="false"
                                    href="#">Update driver Price<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="nav-item">
                        <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                            <input type="hidden" name="action" value="updateDriverPrice">
                            <label class="">Driver Price</label><br>
                             <p>${sessionScope.driverRentalPrice}</p>
                            <label class="">New Price</label><br>
                            <input id="ex" type="text" name="newDriverPrice" style="width: 40%">
                            <button type="submit"
                                    class="btn btn-warning btn-sm">Save</button>
                        </form>
                    </li>
                </ul>
            </li>
            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li role="presentation"><a href="?lang=en">${sessionScope.language['header.lang.eng']}</a></li>
                    <li role="presentation"><a href="?lang=uk">${sessionScope.language['header.lang.ukr']}</a></li>
                </ul>
            </li>
        </ul>

        <form class="navbar-form navbar-left">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="${sessionScope.language['Search']}" name="search">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">
                        <i class="glyphicon glyphicon-search"></i>
                    </button>
                </div>
            </div>
        </form>
        <ul class="nav navbar-nav navbar-right">
            <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                <input type="hidden" name="action" value="logout">
                <button type="submit" class="btn btn-info btn-sm">${sessionScope.language['label.Logout']}</button>
            </form>
        </ul>
    </div>
</nav>

<%--displays all users for admin--%>
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
                            <button type="submit" name="action" value="block"
                                    class="btn btn-danger btn-sm">${sessionScope.language['Block_user']}</button>
                            <button type="submit" name="action" value="unblock"
                                    class="btn btn-success btn-sm">${sessionScope.language['Unblock_user']}</button>
                            <button type="submit" name="action" value="setManager"
                                    class="btn btn-primary btn-sm">${sessionScope.language['Set_manager']}
                            </button>
                            <button type="submit" name="action" value="unsetManager"
                                    class="btn btn-secondary btn-sm">${sessionScope.language['Unset_manager']}
                            </button>
                        </form>
                    </td>
                </tr>
             </c:forEach>
        </tbody>
        <a href="${pageContext.request.contextPath}/view/admin/admin.jsp">${sessionScope.language['Go_back']}</a>
    </table>
</c:if>

<%-- displays all cars for admin --%>
<c:if test="${not empty requestScope.allCars}">
    <table class="table table-hover">
        <c:if test="${requestScope.currentPage == 1}">
            <div class="btn-group dropright" aria-labelledby="dropdownMenuButton">
                <button type="button" class="btn btn-secondary dropdown-toggle"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Number of records
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item"
                       href="?action=findAllCars&page=${requestScope.currentPage}&noOfRecords=5" role="button">5</a>
                    <a class="dropdown-item"
                       href="?action=findAllCars&page=${requestScope.currentPage}&noOfRecords=10" role="button">10</a>
                    <a class="dropdown-item"
                       href="?action=findAllCars&page=${requestScope.currentPage}&noOfRecords=25" role="button">25</a>
                    <a class="dropdown-item"
                       href="?action=findAllCars&page=${requestScope.currentPage}&noOfRecords=50" role="button">50</a>
                </div>
            </div>
        </c:if>
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
                        <button type="submit" name="action" value="getInfoCurrCar"
                                class="btn btn-primary btn-sm">${sessionScope.language['Update']}
                        </button>
                        <button type="submit" name="action" value="deleteCar"
                                class="btn btn-danger btn-sm">${sessionScope.language['Delete']}
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
    <div class="text-center">
        <input  type="hidden" name="action" value="pagination">
        <ul class="pagination justify-content-center">
            <li class="page-item">
                <c:if test="${requestScope.currentPage != 1}">
                <td><a class="page-link"
                       href="?action=findAllCars&page=${requestScope.currentPage - 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Previous']}</a>
                </td>
                </c:if>
            </li>
            <li class="page-item"><a class="page-link"
                                     href="?action=findAllCars&page=1&noOfRecords=${requestScope.noOfRecords}">1</a></li>
            <li class="page-item"><a class="page-link"
                                     href="?action=findAllCars&page=2&noOfRecords=${requestScope.noOfRecords}">2</a></li>
            <li class="page-item"><a class="page-link"
                                     href="?action=findAllCars&page=3&noOfRecords=${requestScope.noOfRecords}">3</a></li>
            <li class="page-item">
                <c:if test="${requestScope.currentPage lt requestScope.amountOfRecords}">
                    <td><a class="page-link"
                           href="?action=findAllCars&page=${requestScope.currentPage + 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Next']}</a>
                    </td>
                </c:if></li>
        </ul>
    </div>
</c:if>

<c:if test="${empty requestScope.allCars and requestScope.currentPage >= 1}">
    <h2>Empty</h2>
</c:if>


<%--displays searched user for admin--%>
<c:if test="${requestScope.searchCommand eq 'searchingUser'}">
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
</c:if>


<%-- displays searched car(s) for admin--%>
<c:if test="${requestScope.searchCommand eq 'searchingCar'}">
     <c:choose>
    <%--    <c:when test="${empty requestScope.searchedCars}">--%>
    <%--        <h3 class="text-center">${sessionScope.language['nothing_was_found']}!</h3>--%>
    <%--    </c:when>--%>
        <c:when test="${not empty requestScope.searchedCars}">
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
                    <c:forEach var="searchedCar" items="${requestScope.searchedCars}">
                         <tr>
                            <td>${searchedCar.carId}</td>
                            <td>${searchedCar.name}</td>
                            <td>${searchedCar.carClass}</td>
                            <td>${searchedCar.brand}</td>
                            <td>${searchedCar.rentalPrice}</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                                    <input type="hidden" name="carId" value="${searchedCar.carId}">
                                    <button type="submit" name="action" value="getInfoCurrCar"
                                            class="btn btn-primary btn-sm">${sessionScope.language['Update']}
                                    </button>
                                    <button type="submit" name="action" value="deleteCar"
                                            class="btn btn-danger btn-sm">${sessionScope.language['Delete']}
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <a href="${pageContext.request.contextPath}/view/admin/admin.jsp">${sessionScope.language['go_home']}</a>
            </table>
        </c:when>
        <c:otherwise>
            <h3 class="text-center">${sessionScope.language['nothing_was_found']}!</h3>
         </c:otherwise>
    </c:choose>
</c:if>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
