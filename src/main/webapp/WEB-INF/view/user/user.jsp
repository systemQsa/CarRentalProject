<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['User_Page']}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

<div class="bg-success text-center">
    <h5>${sessionScope.language['Paying_your_attention_you_can_book_car_from_9_to_19']}!</h5>
</div>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${sessionScope.language['label.Rental_Car']}</a>
        </div>
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="${pageContext.request.contextPath}/view/user/user.jsp">${sessionScope.language['Home']}</a>
                </li>
                <li>
                    <a href="?action=pagination&required=viewMyOrders&page=1&noOfRecords=5">${sessionScope.language['my_orders']}</a>
                </li>
                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"
                                        href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li role="presentation"><a href="?lang=en">${sessionScope.language['header.lang.eng']}</a></li>
                        <li role="presentation"><a href="?lang=uk">${sessionScope.language['header.lang.ukr']}</a></li>
                    </ul>
                </li>
                <jsp:include page="/WEB-INF/view/include/sorter.jsp"/>
                 <li class="dropdown"><a class="dropdown-toggle text-white" data-toggle="dropdown" aria-expanded="false"
                                        href="#"><fmt:formatNumber type="number" pattern=".00" value="${sessionScope.userBalance}"/><span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="nav-item">
                            <form method="post" action="${pageContext.request.contextPath}/helloServlet">
                                <input type="hidden" name="action" value="topUpBalance">
                                <label class="">${sessionScope.language['my_balance']}</label><br>
                                <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                                <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">
                                <input id="ex" type="text" name="topUpBalance" style="width: 40%">
                                <button type="submit"
                                        class="btn btn-warning btn-sm">${sessionScope.language['top_up']}</button>
                            </form>

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
                <div style="margin-top: 15%">
                    <form method="get" action="${pageContext.request.contextPath}/helloServlet">
                    <input type="hidden" name="action" value="logout">
                    <button type="submit"
                            class="btn btn-link">${sessionScope.language['label.Logout']}</button>
                    </form>
                </div>
            </ul>
    </div>
</nav>

<jsp:include page="/WEB-INF/view/info.jsp"/>
<jsp:include page="/WEB-INF/view/allCars.jsp"/>
<jsp:include page="/WEB-INF/view/include/footer.jsp"/>
 </body>
</html>
