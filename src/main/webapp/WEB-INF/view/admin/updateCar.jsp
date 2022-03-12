<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['Update_car']}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<jsp:include page="${pageContext.request.contentType}/WEB-INF/view/include/header.jsp"/>

<h1>UPDATE CAR</h1>
<%--    <c:out value="${requestScope.carName}"/>--%>
<%--    <h4><%=application.getAttribute("carName")%></h4>--%>


<h4>${sessionScope.get("carName")}</h4>

<div class="col-md-6 col-lg-6 offset-lg-3 offset-md-3 mt-4">
    <div class="bg-light p-5 border shadow">
        <h4 class="text-center">${sessionScope.language['Update_car']}</h4>
        <form  method="post" action="${pageContext.request.contextPath}/helloServlet">
            <input type="hidden" name="action" value="updateCar">
            <input type="hidden" name="carId" value="${sessionScope.carId}">

            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label>${sessionScope.language['Name']}  ${sessionScope.carName}</label>
                <input type="text" name="carName"  value="${sessionScope.get("carName")}">
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label>${sessionScope.language['Class']} ${sessionScope.carClass}</label>
                <input type="text"  name="carClass" value="${sessionScope.carClass}">
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label>${sessionScope.language['Brand']}  ${sessionScope.brand}</label>
                <input type="text" name="brand" value="${sessionScope.brand}">
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label>${sessionScope.language['Rental_Price']} ${sessionScope.rentalPrice}</label>
                <input type="text"  name="rentalPrice" value="${sessionScope.rentalPrice}">
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label>${sessionScope.language['Photo']} ${sessionScope.carPhoto}</label>
                <input type="text" name="carPhoto" value="${sessionScope.carPhoto}">
            </div>
            <button type="submit" class="btn btn-primary w-100 my-3 shadow">${sessionScope.language['Update']}</button>
        </form>
    </div>
</div>
<p>${pageContext.request.contextPath}</p>
<a href="${pageContext.request.contextPath}/view/admin/admin.jsp">${sessionScope.language['Go_back']}</a>




<%--<form method="post" action="${pageContext.request.contentType}/car/helloServlet">--%>
<%--    <input type="hidden" name="action" value="updateCar">--%>
<%--    <input type="hidden" name="carId" value="${sessionScope.carId}">--%>

<%--    <label>Car Name  ${sessionScope.carName}</label>--%>
<%--    <input type="text" name="carName"  value="${sessionScope.get("carName")}">--%>

<%--    <label>Car Class  ${sessionScope.carClass}</label>--%>
<%--    <input type="text"  name="carClass" value="${sessionScope.carClass}">--%>

<%--    <label>Car brand  ${sessionScope.brand}</label>--%>
<%--    <input type="text" name="brand" value="${sessionScope.brand}">--%>

<%--    <label>Car Rental price  ${sessionScope.rentalPrice}</label>--%>
<%--    <input type="text"  name="rentalPrice" value="${sessionScope.rentalPrice}">--%>

<%--    <label>Car photo  ${sessionScope.carPhoto}</label>--%>
<%--    <input type="text" name="carPhoto" value="${sessionScope.carPhoto}">--%>
<%--    <button type="submit">UPDATE</button>--%>
<%--</form>--%>

</body>
</html>
