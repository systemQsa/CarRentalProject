<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['User_Page']}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--%>
    <%--    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>--%>
    <%--    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>


<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"--%>
<%--          integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="--%>
<%--          crossorigin="anonymous" referrerpolicy="no-referrer">--%>
<%--    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"--%>
<%--            integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="--%>
<%--            crossorigin="anonymous" referrerpolicy="no-referrer"></script>--%>
<%--    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js"--%>
<%--            integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA=="--%>
<%--            crossorigin="anonymous" referrerpolicy="no-referrer"></script>--%>
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>
<%--<nav class="navbar fixed-top navbar-light bg-light">--%>
<%--    <h4 class="navbar-brand">WELCOME USER</h4>--%>
<%--    <p>${sessionScope.userLogin}</p><br>--%>
<%--    <p>${sessionScope.language['my_balance']}</p><br>--%>
<%--    <h4 class="navbar-brand">${sessionScope.userBalance}</h4>--%>

<%--    <a class="navbar-brand" href="#">${sessionScope.language['label.Rental_Car']}</a>--%>
<%--    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"--%>
<%--            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--        <span class="navbar-toggler-icon"></span>--%>
<%--    </button>--%>
<%--    <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"--%>
<%--                            href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>--%>
<%--        <ul class="dropdown-menu" role="menu">--%>
<%--            <li role="presentation"><a href="?lang=en">ENG</a></li>--%>
<%--            <li role="presentation"><a href="?lang=uk">UKR</a></li>--%>
<%--        </ul>--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        <form class="navbar-form navbar-left" target="_self">--%>
<%--            <div class="form-group">--%>
<%--                <label class="control-label" for="search-field"><i--%>
<%--                        class="glyphicon glyphicon-search"></i></label>--%>
<%--                <input class="form-control search-field" type="search" name="search" id="search-field">--%>
<%--            </div>--%>
<%--        </form>--%>
<%--    </li>--%>

<%--    <div class="collapse navbar-collapse" id="navbarSupportedContent">--%>
<%--        <ul class="navbar-nav mr-auto">--%>
<%--            <li class="nav-item">--%>
<%--            </li>--%>

<%--            <li class="nav-item dropdown">--%>
<%--                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"--%>
<%--                   aria-haspopup="true" aria-expanded="false">--%>
<%--                    ${sessionScope.language['sort_by']}--%>
<%--                </a>--%>
<%--                <div class="dropdown-menu" aria-labelledby="navbarDropdown">--%>
<%--                    <a href="?sort=byName&action=wantedOrder&order=sortCarsByName" role="button"--%>
<%--                       class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_name']}</a>--%>

<%--                    <a href="?sort=byClass&action=wantedOrder&order=sortCarsByClass" role="button"--%>
<%--                       class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_class']}</a>--%>

<%--                    <a href="?sort=byBrand&action=wantedOrder&order=sortCarsByBrand" role="button"--%>
<%--                       class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_brand']}</a>--%>

<%--                    <a href="?sort=byPrice&action=wantedOrder&order=sortCarsByRentPrice" role="button"--%>
<%--                       class="btn btn-outline-primary my-2 my-sm-0 btn-sm">${sessionScope.language['sort_by_price']}</a>--%>


<%--                    <div class="dropdown-divider"></div>--%>
<%--                    <a class="dropdown-item" href="#">Something else here</a>--%>
<%--                </div>--%>
<%--            </li>--%>
<%--            <li class="nav-item">--%>
<%--                <form method="post" action="${pageContext.request.contextPath}/helloServlet">--%>
<%--                    <input type="hidden" name="action" value="topUpBalance">--%>
<%--                    <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">--%>
<%--                    <input type="hidden" name="userIdByLogin" value="${sessionScope.userIdByLogin}">--%>
<%--                    <input id="ex" type="text" name="topUpBalance" style="width: 20%">--%>
<%--                    <button type="submit"--%>
<%--                            class="btn btn-outline-warning my-2 my-sm-0 btn-sm">${sessionScope.language['top_up']}</button>--%>
<%--                </form>--%>
<%--            </li>--%>
<%--            <li class="nav-item">--%>
<%--                <a href="?action=pagination&required=viewMyOrders&page=1">${sessionScope.language['my_orders']}</a>--%>
<%--            </li>--%>

<%--        </ul>--%>

<%--        <form method="get" action="${pageContext.request.contextPath}/helloServlet" class="form-inline my-2 my-lg-0">--%>
<%--            <input type="hidden" name="action" value="logout">--%>
<%--            <button type="submit"--%>
<%--                    class="btn btn-outline-secondary my-2 my-sm-0 btn-sm">${sessionScope.language['label.Logout']}</button>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--</nav>--%>

<div class="bg-success text-center">
<h5>${sessionScope.language['Paying_your_attention_you_can_book_car_from_9_to_19']}!</h5>
</div>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${sessionScope.language['label.Rental_Car']}</a>
        </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="?action=pagination&required=viewMyOrders&page=1&noOfRecords=5">${sessionScope.language['my_orders']}</a></li>
                <li><a href="#">Page 2</a></li>
                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"
                                        href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li role="presentation"><a href="?lang=en">${sessionScope.language['header.lang.eng']}</a></li>
                        <li role="presentation"><a href="?lang=uk">${sessionScope.language['header.lang.ukr']}</a></li>
                    </ul>
                </li>
                <jsp:include page="/WEB-INF/view/include/sorter.jsp"/>
                 <li class="dropdown"><a class="dropdown-toggle text-white" data-toggle="dropdown" aria-expanded="false"
                                        href="#">${sessionScope.userBalance}<span class="caret"></span></a>
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

<jsp:include page="/WEB-INF/view/allCars.jsp"/>

 </body>
</html>
