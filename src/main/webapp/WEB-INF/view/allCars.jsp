<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>All Cars</title>
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
                               <button type="button" onclick="alert('To book please login!')"
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
            <c:if test="${not empty requestScope.allCars}">
        <div class="text-center">
                <%--pagination--%>
            <input type="hidden" name="action" value="pagination">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <c:if test="${requestScope.currentPage != 1}">
                        <td><a class="page-link"
                               href="?action=pagination&required=viewCars&page=${requestScope.currentPage - 1}">${sessionScope.language['Previous']}</a>
                        </td>
                    </c:if>
                </li>
                <li class="page-item"><a class="page-link" href="?action=pagination&required=viewCars&page=1">1</a></li>
                <li class="page-item"><a class="page-link" href="?action=pagination&required=viewCars&page=2">2</a></li>
                <li class="page-item"><a class="page-link" href="?action=pagination&required=viewCars&page=3">3</a></li>
                <li class="page-item">
                    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                        <td><a class="page-link"
                               href="?action=pagination&required=viewCars&page=${requestScope.currentPage + 1}">${sessionScope.language['Next']}</a>
                        </td>
                    </c:if></li>
                </c:if>
            </ul>
            </c:when>
            <c:when test="${not empty requestScope.sortedCars}">
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
                    <c:otherwise>
                        <div class="text-center">
                            <h2> There are no cars yet!</h2>
                        </div>
                    </c:otherwise>
                    </c:choose>
                </div>
            </table>

            <c:if test="${not empty requestScope.sortedCars}">
                <div class="text-center">
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <c:if test="${requestScope.currentPage != 1}">
                                <td><a class="page-link"
                                       href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage - 1}">${sessionScope.language['Previous']}</a>
                                </td>
                            </c:if>
                        </li>
                        <li class="page-item"><a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=1">1</a></li>
                        <li class="page-item"><a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=2">2</a></li>
                        <li class="page-item"><a class="page-link" href="?sort=sorting&action=wantedOrder&order=${requestScope.order}&page=3">3</a></li>
                       <li class="page-item"> <c:if test="${requestScope.currentPage lt sessionScope.totalPages}">
                            <td><a class="page-link"
                                   href="?sort=byName&action=wantedOrder&order=${requestScope.order}&page=${requestScope.currentPage + 1}">${sessionScope.language['Next']}</a>
                            </td>
                       </c:if></li>
                    </ul>
                </div>
            </c:if>
        </div>

    </div>


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
        <td>${requestScope.searchedCar.carId}</td>
        <td>${requestScope.searchedCar.name}</td>
        <td>${requestScope.searchedCar.carClass}</td>
        <td>${requestScope.searchedCar.brand}</td>
        <td>${requestScope.searchedCar.rentalPrice}</td>
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
                        <input type="hidden" name="carId" value="${requestScope.searchedCar.carId}">
                        <input type="hidden" name="carName" value="${requestScope.searchedCar.name}">
                        <input type="hidden" name="carClass" value="${requestScope.searchedCar.carClass}">
                        <input type="hidden" name="carBrand" value="${requestScope.searchedCar.brand}">
                        <input type="hidden" name="rentPrice" value="${requestScope.searchedCar.rentalPrice}">
                        <button type="submit"
                                class="btn btn-warning btn-sm">${sessionScope.language['Book_Car']}</button>
                    </form>
                </c:when>
            </c:choose>
        </td>
        </tr>
        </tbody>
    </table>
    </c:when>
    </c:choose>


</body>
</html>
