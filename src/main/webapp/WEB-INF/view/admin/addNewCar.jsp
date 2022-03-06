<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${sessionScope.language['add_new_car']}</title>
</head>
<body>

<jsp:include page="${pageContext.request.contentType}/WEB-INF/view/include/header.jsp"/>
<div class="col-md-6 col-lg-6 offset-lg-3 offset-md-3 mt-4">
    <div class="bg-light p-5 border shadow">
        <h4 class="text-center">${sessionScope.language['add_new_car']}</h4>
        <form  method="post" action="${pageContext.request.contentType}/car/helloServlet">
            <input type="hidden" name="action" value="addCar">
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label class="form-label" for="carName">${sessionScope.language['Name']}</label>
                <input type="text" id="carName" class="form-control" name="name"/>
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label class="form-label" for="carClass">${sessionScope.language['Class']}</label>
                <input type="text" id="carClass" class="form-control" name="carClass"/>
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label class="form-label" for="carBrand">${sessionScope.language['Brand']}</label>
                <input type="text" id="carBrand" class="form-control" name="brand"/>
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label class="form-label" for="rentalPrice">${sessionScope.language['Rental_Price']}</label>
                <input type="text" id="rentalPrice" class="form-control" name="rentalPrice"/>
            </div>
            <div class="form-outline col-md-5 col-lg-5 offset-lg-3 offset-md-3 mt-4">
                <label class="form-label" for="photo">${sessionScope.language['Photo']}</label>
                <input type="text" id="photo" class="form-control" name="photo"/>
            </div>
            <button type="submit" class="btn btn-primary w-100 my-3 shadow">${sessionScope.language['Add']}</button>
        </form>
    </div>
</div>

<a href="${pageContext.request.contextPath}/view/admin/admin.jsp">${sessionScope.language['Go_back']}</a>


<%--<h1>ADD NEW CAR</h1>--%>
<%--<form  method="post" action="${pageContext.request.contentType}/car/helloServlet">--%>
<%--    <input type="hidden" name="action" value="addCar">--%>
<%--    <label>CAR NAME</label>--%>
<%--    <input type="text" name="name">--%>
<%--    <label>CAR CLASS</label>--%>
<%--    <input type="text" name="carClass">--%>
<%--    <label>CAR BRAND</label>--%>
<%--    <input type="text" name="brand">--%>
<%--    <label>CAR RENTAL PRICE</label>--%>
<%--    <input type="text" name="rentalPrice">--%>
<%--    <label>CAR PHOTO</label>--%>
<%--    <input type="image" name="photo">--%>
<%--    <button type="submit">ADD CAR</button>--%>
<%--</form>--%>

</body>
</html>
