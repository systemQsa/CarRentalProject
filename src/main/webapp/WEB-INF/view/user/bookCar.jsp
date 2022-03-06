<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</head>
<body>
 <h1 class="text-center">${sessionScope.language['Book_Car']}</h1>
<span class="col-md-6 col-lg-6 offset-lg-3 offset-md-3 mt-4">
    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
        <input type="hidden" name="action" value="countReceipt">
        <input type="hidden" name="userLogin" value="${requestScope.userLogin}">
        <input type="hidden" name="carId" value="${sessionScope.carIdReq}">
        <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">
        <input type="hidden" name="userBalance" value="${sessionScope.userBalance}">
        <span class="d-block p-2">
            <label>${sessionScope.language['Name']}</label>
            <input type="hidden" name="carName" value="${sessionScope.carNameReq}">
            <p>${sessionScope.carNameReq}</p>
       </span>
        <span class="d-block p-2">
            <label>${sessionScope.language['Class']}</label>
            <input type="hidden" name="carClass" value="${sessionScope.carClassReq}">
            <p>${sessionScope.carClassReq}</p>
        </span>
        <span class="d-block p-2">
             <label>${sessionScope.language['Brand']}</label>
            <input type="hidden" name="carBrand" value="${sessionScope.carBrandReq}">
            <p>${sessionScope.carBrandReq}</p>
        </span>
        <span class="d-block p-2 ">
                 <label>${sessionScope.language['Rental_Price']}</label>
                <input type="hidden" name="carRentPrice" value="${sessionScope.rentPriceReq}">
                <p>${sessionScope.rentPriceReq}</p>

        </span>
       <span class="d-block p-2">
            <label>${sessionScope.language['Passport']}</label>
            <input type="text" name="userPassport">
           <c:if test="${requestScope.err == 12}">
               <p>${requestScope.errMSG}</p>
           </c:if>
       </span>
        <span class="d-block p-2 ">
             <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" value="true">
                <label class="form-check-label" for="flexRadioDefault1">
                    <p>${sessionScope.language['With_driver']} + 100</p>
                 </label>
            </div>
        </span>
        <span class="d-block p-2 ">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked value="false">
                <label class="form-check-label" for="flexRadioDefault2">
                  <p>${sessionScope.language['Without_driver']}</p>
                </label>
            </div>
        </span>
     <span class="d-block p-2">
            <div class='col-md-5'>
                <div class="form-group">
                    <label class="control-label">From Date</label>
                    <div class='input-group date' id='datetimepicker1'>
                        <input type='text' class="form-control" name="fromDate"/>
                        <span class="input-group-addon">
                             <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>


            <div class='col-md-5'>
                <div class="form-group">
                    <label class="control-label">To date</label>
                    <div class='input-group date' id='datetimepicker2'>
                        <input type='text' class="form-control" name="toDate"/>
                        <span class="input-group-addon">
                             <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
         <c:if test="${requestScope.err == 10}">
             <p>${requestScope.errMSG}</p>
         </c:if>
        </span>

        <button type="submit" class="btn btn-primary btn-sm">${sessionScope.language['Count']}</button>
    </form>
</div>


</span>


<script type="text/javascript" src="<c:url value='/js/date.js'/>"></script>

</body>
</html>
