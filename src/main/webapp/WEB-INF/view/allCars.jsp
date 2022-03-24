<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['All_cars']}</title>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css"/> ">
</head>
<body>


<h3>${sessionScope.language['Cars']}</h3>
<%--<c:if test="${ not empty requestScope.amountOfRecords}">--%>
<%--    <p>${requestScope.amountOfRecords}</p>--%>
<%--</c:if>--%>

<%--<c:if test="${ not empty requestScope.noOfPages}">--%>
<%--    <p>${requestScope.noOfPages}</p>--%>
<%--</c:if>--%>


<%--    <p>records</p>--%>
<%--    <p>${sessionScope.records}</p>--%>

<%--<p>curr page</p>--%>
<%--<p>${requestScope.currentPage}</p>--%>


<div class="row col-md-9 col-md-push-2">
    <div>
        <c:choose>
        <c:when test="${not empty requestScope.allCars}">
        <table class="table table-hover">

<%--            <c:if test="${requestScope.currentPage == 1}">--%>

<%--            <div class="dropdown show">--%>
<%--                <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--                    Dropdown link--%>
<%--                </a>--%>

<%--                <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">--%>
<%--                    <a class="dropdown-item" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&records=10">10</a>--%>
<%--                    <a class="dropdown-item" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&records=25">25</a>--%>
<%--                    <a class="dropdown-item" href="#">Something else here</a>--%>
<%--                </div>--%>
<%--            </div>--%>


                <div class="btn-group dropright" aria-labelledby="dropdownMenuButton">
                    <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Dropright
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=5" role="button">5</a>
                        <a class="dropdown-item" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=10" role="button">10</a>
                        <a class="dropdown-item" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=25" role="button">25</a>
                        <a class="dropdown-item" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=50" role="button">50</a>
                    </div>
                </div>
<%--            </c:if>--%>

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
                    <th scope="row">${car.carId}</th>
                    <td>${car.name}</td>
                    <td>${car.carClass}</td>
                    <td>${car.brand}</td>
                    <td>${car.rentalPrice}</td>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.role == null}">
<%--                               <button type="button"--%>
<%--                                        class="btn btn-warning btn-sm"--%>
<%--                                       data-target="#notLogedUser" data-toggle="modal">${sessionScope.language['Book_Car']}</button>--%>
                                <a href="#modalColor" data-target-color="lightblue" data-toggle="modal" class="btn btn-warning"role="button">${sessionScope.language['Book_Car']}</a>
                            </c:when>
                            <c:when test="${sessionScope.role eq 'user'}">

                                <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                                    <input type="hidden" name="action" value="bookCarReq">
                                    <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                                    <input type="hidden" name="carId" value="${car.carId}">
                                    <input type="hidden" name="carName" value="${car.name}">
                                    <input type="hidden" name="carClass" value="${car.carClass}">
                                    <input type="hidden" name="carBrand" value="${car.brand}">
                                    <input type="hidden" name="rentPrice" value="${car.rentalPrice}">
                                    <button type="submit"
                                            class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>
                                </form>
                            </c:when>
                        </c:choose>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
            <c:if test="${not empty requestScope.allCars}">
        <div class="text-center">
                <%--pagination--%>
            <input type="hidden" name="action" value="pagination">
<%--                    <c:if test="${requestScope.noOfRecords <= requestScope.amountOfRecordsTotal}">--%>
                        <ul class="pagination justify-content-center">
                            <li class="page-item">
                                <c:if test="${requestScope.currentPage != 1}">
                                    <td><a class="page-link"
                                           href="?action=pagination&required=viewCars&page=${requestScope.currentPage - 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Previous']}</a>
                                    </td>
                                </c:if>
                            </li>
                            <li class="page-item"><a class="page-link" href="?action=pagination&required=viewCars&page=1&noOfRecords=${requestScope.noOfRecords}">1</a></li>
                            <li class="page-item"><a class="page-link" href="?action=pagination&required=viewCars&page=2&noOfRecords=${requestScope.noOfRecords}">2</a></li>
                            <li class="page-item"><a class="page-link" href="?action=pagination&required=viewCars&page=3&noOfRecords=${requestScope.noOfRecords}">3</a></li>
                            <li class="page-item">
                                <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                    <td><a class="page-link"
                                           href="?action=pagination&required=viewCars&page=${requestScope.currentPage + 1}&noOfRecords=${requestScope.noOfRecords}">${sessionScope.language['Next']}</a>
                                    </td>
                                </c:if></li>
                            </c:if>
                        </ul>
<%--                    </c:if>--%>
            </c:when>
        <c:when test="${not empty requestScope.sortedCars}">
            <table class="table table-hover">
                <div class="btn-group dropright" aria-labelledby="dropdownMenuButton">
                    <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Amount of records
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOfRecords=5" role="button">5</a>
                        <a class="dropdown-item" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOfRecords=10" role="button">10</a>
                        <a class="dropdown-item" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOfRecords=25" role="button">25</a>
                        <a class="dropdown-item" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOfRecords=50" role="button">50</a>
                    </div>
                </div>
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

                <c:forEach var="car" items="${requestScope.sortedCars}">
                    <tr>
                        <th scope="row">${car.carId}</th>
                        <td>${car.name}</td>
                        <td>${car.carClass}</td>
                        <td>${car.brand}</td>
                        <td>${car.rentalPrice}</td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.role == null}">
                                    <button type="button"
                                            class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>
                                </c:when>
                                <c:when test="${sessionScope.role eq 'user'}">

                                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                                        <input type="hidden" name="action" value="bookCarReq">
                                        <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                                        <input type="hidden" name="carId" value="${car.carId}">
                                        <input type="hidden" name="carName" value="${car.name}">
                                        <input type="hidden" name="carClass" value="${car.carClass}">
                                        <input type="hidden" name="carBrand" value="${car.brand}">
                                        <input type="hidden" name="rentPrice" value="${car.rentalPrice}">
                                        <button type="submit"
                                                class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>
                                    </form>
                                </c:when>
                            </c:choose>

                        </td>
                    </tr>
                </c:forEach>

                </tbody>

                        <%--pagination--%>
<%--                    <input type="hidden" name="action" value="pagination">--%>
<%--                    <ul class="pagination justify-content-center">--%>
<%--                        <li class="page-item">--%>
<%--                            <c:if test="${requestScope.currentPage != 1}">--%>
<%--                                <td><a class="page-link"--%>
<%--                                       href="?action=pagination&required=viewCars&page=${requestScope.currentPage - 1}">${sessionScope.language['Previous']}</a>--%>
<%--                                </td>--%>
<%--                            </c:if></li>--%>
<%--                        <li class="page-item"><a class="page-link"--%>
<%--                                                 href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=1">1</a>--%>
<%--                        </li>--%>
<%--                        <li class="page-item"><a class="page-link"--%>
<%--                                                 href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=2">2</a>--%>
<%--                        </li>--%>
<%--                        <li class="page-item"><a class="page-link"--%>
<%--                                                 href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=3">3</a>--%>
<%--                        </li>--%>
<%--                        <li class="page-item">--%>
<%--                            <c:if test="${requestScope.currentPage lt requestScope.records}">--%>
<%--                                <td><a class="page-link"--%>
<%--                                       href="?action=pagination&required=viewCars&page=${requestScope.currentPage + 1}">${sessionScope.language['Next']}</a>--%>
<%--                                </td>--%>
<%--                            </c:if></li>--%>
<%--                    </ul>--%>

            </c:when>
<%--                    <c:otherwise>--%>
<%--                        <div class="text-center">--%>
<%--                            <h2> ${sessionScope.language['there_are_no_cars_yet']}!</h2>--%>
<%--                        </div>--%>
<%--                    </c:otherwise>--%>
                    </c:choose>
                </div>
            </table>

            <c:if test="${not empty requestScope.sortedCars}">
                <div class="text-center">
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <c:if test="${requestScope.currentPage != 1}">
                                <td><a class="page-link"
                                       href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage - 1}&noOrRecords=${requestScope.noOfRecords}">${sessionScope.language['Previous']}</a>
                                </td>
                            </c:if>
                        </li>
                        <li class="page-item"><a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=1&noOrRecords=${requestScope.noOfRecords}">1</a></li>
                        <li class="page-item"><a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=2&noOrRecords=${requestScope.noOfRecords}">2</a></li>
                        <li class="page-item"><a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=3&noOrRecords=${requestScope.noOfRecords}">3</a></li>
                       <li class="page-item"> <c:if test="${requestScope.currentPage lt sessionScope.totalPages}">
                            <td><a class="page-link"
                                   href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage + 1}&noOrRecords=${requestScope.noOfRecords}">${sessionScope.language['Next']}</a>
                            </td>
                       </c:if></li>
                    </ul>
                </div>
            </c:if>
        </div>

    </div>


        <c:choose>
        <c:when test="${empty requestScope.searchedCars and empty requestScope.allCars and empty requestScope.sortedCars}">
        <h3 class="text-center">${sessionScope.language['nothing_was_found']}!</h3>
        </c:when>
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
                <td>${searchedCar.carId}</td>
                <td>${searchedCar.name}</td>
                <td>${searchedCar.carClass}</td>
                <td>${searchedCar.brand}</td>
                <td>${searchedCar.rentalPrice}</td>
                <td>
                    <c:choose>
                        <c:when test="${sessionScope.role == null}">
<%--                            <button type="button"--%>
<%--                                    class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>--%>
                            <a href="#modalColor" data-target-color="lightblue" data-toggle="modal" class="btn btn-warning"role="button">${sessionScope.language['Book_Car']}</a>
                        </c:when>
                        <c:when test="${sessionScope.role eq 'user'}">
                            <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                                <input type="hidden" name="action" value="bookCarReq">
                                <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                                <input type="hidden" name="carId" value="${searchedCar.carId}">
                                <input type="hidden" name="carName" value="${searchedCar.name}">
                                <input type="hidden" name="carClass" value="${searchedCar.carClass}">
                                <input type="hidden" name="carBrand" value="${searchedCar.brand}">
                                <input type="hidden" name="rentPrice" value="${searchedCar.rentalPrice}">
                                <button type="submit"
                                        class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>
                            </form>
                        </c:when>
                    </c:choose>
                </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:when>
        </c:choose>


<%--<div class="modal fade" id="notLogedUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">--%>
<%--    <div class="modal-dialog" role="document">--%>
<%--        <div class="modal-content">--%>
<%--            <div class="modal-header">--%>
<%--                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
<%--                <h4 class="modal-title" id="exampleModalLabel">Please login!</h4>--%>
<%--            </div>--%>
<%--            <div class="modal-body">--%>
<%--                <form method="post" action="helloServlet">--%>
<%--                    <input type="hidden" name="action" value="forgotPass">--%>
<%--                    <input type="hidden" name="setCommand" value="checkSecretCode">--%>
<%--                    <div class="form-group">--%>
<%--                        <h6>Please login to book the car</h6>--%>
<%--                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-info w-100 my-3 shadow" role="button">${sessionScope.language['label.login']}</a>--%>
<%--                    </div>--%>
<%--                </form>--%>
<%--            </div>--%>
<%--            <div class="modal-footer">--%>
<%--                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<div class="modal fade" data-modal-color="" id="modalColor" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h4 class="modal-title">${sessionScope.language['seems_you_are_not_logged_yet']}</h4>
            </div>
            <div class="modal-body bg-info">
                <p>${sessionScope.language['to_make_booking_you_need_to_login_if_you_don`t_have_account_register']}</p>
            </div>
            <div class="modal-footer bg-info">
                <a href="${pageContext.request.contextPath}/login.jsp">${sessionScope.language['label.login']}</a>
<%--                <button type="button" class="btn btn-link">${sessionScope.language['label.login']}</button>--%>
                <button type="button" class="btn btn-link" data-dismiss="modal">${sessionScope.language['Close']}</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
