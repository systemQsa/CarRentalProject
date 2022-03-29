<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['All_cars']}</title>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css"/> ">
</head>
<body>

<div class="row col-md-10 col-md-push-1">
    <div>
        <c:choose>
<%--        displays cars in casual order desc--%>
            <c:when test="${not empty requestScope.allCars}">
                 <table class="table table-hover">

<%--            <c:if test="${requestScope.currentPage == 1}">--%>

<%--            <div class="dropdown show">--%>
<%--                <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--                    Dropdown link--%>
<%--                </a>--%>

                  <c:if test="${requestScope.currentPage == 1}">
                    <ul class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['Amount']}<span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=5">5</a></li>
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=10">10</a></li>
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=25">25</a></li>
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?action=pagination&required=viewCars&page=${requestScope.currentPage}&noOfRecords=50">50</a></li>
                         </ul>
                    </ul>
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
                            <th scope="row">${car.carId}</th>
                            <td>${car.name}</td>
                            <td>${car.carClass}</td>
                            <td>${car.brand}</td>
                            <td>${car.rentalPrice}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.role == null}">
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
<%--                    displays cars in desired order--%>
            <c:when test="${not empty requestScope.sortedCars}">
                <table class="table table-hover">
                 <c:if test="${requestScope.currentPage == 1}">
                    <ul class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['Amount']}<span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOrRecordsSorted=5" role="button">5</a></li>
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOrRecordsSorted=10" role="button">10</a></li>
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOrRecordsSorted=25" role="button">25</a></li>
                            <li role="presentation"><a class="dropdown-item text-decoration-none" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage}&noOrRecordsSorted=50" role="button">50</a></li>
                        </ul>
                    </ul>
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
                </table>
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
                <c:otherwise>
<%--                    <div class="text-center">--%>
<%--                        <h2>${sessionScope.language['nothing_was_found']}!</h2>--%>
<%--                    </div>--%>
<%--                    <br><br><br><br>--%>
                </c:otherwise>
        </c:choose>
        <br><br><br><br>
    </div>

<%--                </table>--%>

    <c:if test="${not empty requestScope.sortedCars}">
            <div class="text-center">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <c:if test="${requestScope.currentPage != 1}">
                            <td>
                                <a class="page-link"
                                   href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage - 1}&noOrRecordsSorted=${requestScope.noOrRecordsSorted}">${sessionScope.language['Previous']}</a>
                            </td>
                        </c:if>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=1&noOrRecordsSorted=${requestScope.noOrRecordsSorted}">1</a></li>
                    <li class="page-item">
                        <a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=2&noOrRecordsSorted=${requestScope.noOrRecordsSorted}">2</a></li>
                    <li class="page-item">
                        <a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=3&noOrRecordsSorted=${requestScope.noOrRecordsSorted}">3</a></li>
                    <li class="page-item"> <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                        <td><a class="page-link"
                               href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage + 1}&noOrRecordsSorted=${requestScope.noOrRecordsSorted}">${sessionScope.language['Next']}</a>
                        </td>
                   </c:if></li>
                </ul>
            </div>
            </c:if>
        </div>

</div>


<div class="row col-md-10 col-md-push-1">
    <%--search desired car(s)--%>
    <c:choose>
        <c:when test="${empty requestScope.searchedCars and empty requestScope.allCars and empty requestScope.sortedCars}">
            <br><br><br><br>

            <div class="h-100 row align-items-center">
                <div class="col">
                    <h2 class="text-center">${sessionScope.language['nothing_was_found']}!</h2>
                </div>
            </div>
            <br><br><br><br>
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


    <%--modal window--%>
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
                    <a href="${pageContext.request.contextPath}/login.jsp">${sessionScope.language['label.Login']}</a>
                     <button type="button" class="btn btn-link" data-dismiss="modal">${sessionScope.language['Close']}</button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
