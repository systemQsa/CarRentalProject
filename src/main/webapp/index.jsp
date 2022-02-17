<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental Car</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <%--    <link rel="stylesheet" href="css/index2.css">--%>
    <style><%@include file="css/index2.css"%></style>
</head>
<body>
<div>
    <div class="header-blue">
        <nav class="navbar navbar-default navigation-clean-search">
            <div class="container">
                <div class="navbar-header"><a class="navbar-brand navbar-link" href="#">${language['label.Rental_Car']}</a>
                    <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
                </div>
                <div class="collapse navbar-collapse" id="navcol-1">
                    <ul class="nav navbar-nav">
                        <li><a href="${pageContext.request.contextPath}/index.jsp">
                            <p>${language['header.Home']}</p>
                        </a></li>
                        <li><a href="#"><p>${language['header.fleet']}</p></a></li>
                        <li><a href="#"> <p>${language['header.contact_us']}</p></a>
                        <li><a href="#"><p>${language['header.services_upper']}</p></a></li>
                        <li><a href="#"><p>${language['header.Make_a_booking']}</p></a></li>

                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${language['label.Language']}<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li role="presentation"><a href="?lang=en">ENG</a></li>
                                <li role="presentation"><a href="?lang=uk">UKR</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-left" target="_self">
                        <div class="form-group">
                            <label class="control-label" for="search-field"><i class="glyphicon glyphicon-search"></i></label>
                            <input class="form-control search-field" type="search" name="search" id="search-field">
                        </div>
                    </form>
                    <form action="helloServlet" method="post">
                        <p class="navbar-text navbar-right"><a class="navbar-link login" href="${pageContext.request.contextPath}/login.jsp">${language['label.Login']}</a>
                    </form>
                    <a class="btn btn-default action-button" role="button" href="${pageContext.request.contextPath}/register.jsp">${language['label.Register']}</a></p>
                </div>
            </div>
        </nav>
        <div class="container hero">
            <div class="row">
                <div class="col-lg-5 col-lg-offset-1 col-md-6 col-md-offset-0">
                    <h2>${pageContext.session.servletContext.getAttribute("userName")}</h2>
                    <h1>The revolution is here.</h1>
                    <p>Mauris egestas tellus non ex condimentum, ac ullamcorper sapien dictum. Nam consequat neque quis sapien viverra convallis. In non tempus lorem. </p>
                    <button class="btn btn-default btn-lg action-button" type="button">Learn More</button>
                </div>
            </div>
        </div>
    </div>
</div>
<a href="${pageContext.request.contentType}/index2.jsp"></a>
<a href="${pageContext.request.contentType}/car/connect.jsp">Connection Check</a>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>
