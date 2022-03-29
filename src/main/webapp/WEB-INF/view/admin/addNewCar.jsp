<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${sessionScope.language['add_new_car']}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>
<jsp:include page="/WEB-INF/view/errorMSG.jsp"/>

<div class="col-md-6 col-lg-6 offset-lg-3 offset-md-3 mt-4">
    <div class="bg-light p-5 border shadow">

        <h4 class="text-center">${sessionScope.language['add_new_car']}</h4>
        <form  method="post"  action="${pageContext.request.contextPath}/helloServlet">
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
            <button type="submit" class="btn btn-primary w-100 my-3 shadow">${sessionScope.language['Add']}</button>
        </form>
    </div>
    <a href="${pageContext.request.contextPath}/view/admin/admin.jsp">${sessionScope.language['Go_back']}</a>
</div>

</body>
</html>
