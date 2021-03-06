<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['Book_Car']}</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">


</head>
<body>
<jsp:include page="/WEB-INF/view/errorMSG.jsp"/>

<div class="container">

    <div class="col-md-6 col-lg-6 offset-lg-3 offset-md-3 mt-4">
             <div class="bg-light p-5 border shadow">
                 <h3 class="text-center"><strong>${sessionScope.language['Book_Car']}</strong></h3>
                    <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                    <input type="hidden" name="action" value="countReceipt">
                    <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                    <input type="hidden" name="carId" value="${sessionScope.carIdReq}">
                    <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">
                    <input type="hidden" name="userBalance" value="${sessionScope.userBalance}">

                    <div class="form-group-row d-block p-2">
                        <span class="col-md-7"><strong>${sessionScope.language['Name']}</strong></span>
                        <span >${sessionScope.carNameReq}</span>
                        <input type="hidden" name="carName" value="${sessionScope.carNameReq}">
                    </div>

                    <div class="form-group-row d-block p-2">
                        <span class="col-md-7"><strong>${sessionScope.language['Class']}</strong></span>
                        <span >${sessionScope.carClassReq}</span>
                        <input type="hidden" name="carClass" value="${sessionScope.carClassReq}">
                    </div>

                    <div class="form-group-row d-block p-2">
                        <span class="col-md-7"><strong>${sessionScope.language['Brand']}</strong></span>
                        <span >${sessionScope.carBrandReq}</span>
                        <input type="hidden" name="carBrand" value="${sessionScope.carBrandReq}">
                    </div>

                    <div class="form-group-row d-block p-2">
                        <span class="col-md-7"><strong>${sessionScope.language['Rental_Price']}</strong></span>
                        <input type="hidden" name="carRentPrice" value="${sessionScope.rentPriceReq}">
                        <span ><fmt:formatNumber type="number" pattern=".00" value="${sessionScope.rentPriceReq}"/> </span>

                    </div>

                    <div class="form-group-row d-block p-2">
                        <span class="col-md-6"><strong>${sessionScope.language['Passport']}</strong></span>
                        <div class="col-xs-2">
                            <input type="text" name="userPassport"/>
                        </div>
<%--                         <c:if test="${requestScope.err == 12}">--%>
<%--                            <p>${requestScope.errMSG}</p>--%>
<%--                        </c:if>--%>
                     </div>

                        <div class="form-group-row d-block p-4">
    <%--                        radio button check--%>
                            <h5><strong>${sessionScope.language['Driver_rental_price']}
                                <fmt:formatNumber type="number" pattern=".00" value="${sessionScope.driverRentalPrice}"/></strong></h5>
                            <label class="radio-inline">
                                <input  class="form-check-input" type="radio" name="withOrWithoutDriver"  value="false" checked>
                                <strong>${sessionScope.language['Without_driver']}</strong>
                            </label>
                            <label class="radio-inline">
                                <input class="form-check-input" type="radio" name="withOrWithoutDriver" value="true">
                                <strong>${sessionScope.language['With_driver']}</strong>
                            </label>
                        </div>

                   <span class="d-block p-2">
                        <div class='col-md-5'>
                            <div class="form-group">
                                <label class="control-label">${sessionScope.language['From_date']}</label>
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
                                <label class="control-label">${sessionScope.language['To_date']}</label>
                                <div class='input-group date' id='datetimepicker2'>
                                    <input type='text' class="form-control" name="toDate"/>
                                    <span class="input-group-addon">
                                         <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                   </span>
                        <button type="submit" class="btn btn-primary w-100 my-3 shadow">${sessionScope.language['Count']}</button>
                </form>
             </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value='/js/date.js'/>"></script>

</body>
</html>
