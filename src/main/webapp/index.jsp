<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="out" uri="mytag" %>
<!DOCTYPE html>
<html>
<link>
    <meta charset="utf-8">
    <title>${sessionScope.language['label.Rental_Car']}</title>
     <%--    <link rel="stylesheet" href="css/index2.css">--%>
<%--    <style><%@include file="css/index.css"%></style>--%>
<%--  <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/css/index.css"/>">--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" type="text/css">

</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

<%--<div>--%>
<%--    <div class="header-blue">--%>
<%--        <nav class="navbar navbar-default navigation-clean-search">--%>
<%--            <div class="container">--%>
<%--                <div class="navbar-header"><a class="navbar-brand navbar-link" href="#">${sessionScope.language['label.Rental_Car']}</a>--%>
<%--                    <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>--%>
<%--                </div>--%>
<%--                <div class="collapse navbar-collapse" id="navcol-1">--%>
<%--                    <ul class="nav navbar-nav">--%>
<%--                        <li><a href="${pageContext.request.contextPath}/index.jsp">--%>
<%--&lt;%&ndash;                            <p><fmt:message bundle="${locale}" key="header.Home"/></p>&ndash;%&gt;--%>
<%--                        </a></li>--%>
<%--&lt;%&ndash;                        <li><a href="#"><p><fmt:message bundle="${locale}" key="header.fleet"/></p></a></li>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <li><a href="#"><p><fmt:message bundle="${locale}" key="header.contact_us"/></p></a>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <li><a href="#"><p><fmt:message bundle="${locale}" key="header.services_upper"/></p></a></li>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <li><a href="#"><p><fmt:message bundle="${locale}" key="header.Make_a_booking"/></p></a></li>&ndash;%&gt;--%>
<%--                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>--%>
<%--                            <ul class="dropdown-menu" role="menu">--%>
<%--&lt;%&ndash;                                <form action="${pageContext.request.contentType}/car/helloServlet" method="post">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                                    <input type="hidden" name="action" value="switchLang">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                                    <input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                                    <input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                    <button type="submit" name="language" value="uk"><fmt:message bundle="${locale}" key="header.lang.ukr"/></button>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                    <button type="submit" name="language" value="en"><fmt:message bundle="${locale}" key="header.lang.eng"/></button>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                </form>&ndash;%&gt;--%>
<%--                                <li role="presentation"><a href="?lang=en">ENG</a></li>--%>
<%--                                <li role="presentation"><a href="?lang=uk">UKR</a></li>--%>
<%--                            </ul>--%>
<%--                        </li>--%>
<%--                        <li><a href="${pageContext.request.contextPath}/register.jsp">${sessionScope.language['label.Register']}</a></li>--%>
<%--                    </ul>--%>
<%--                    <form class="navbar-form navbar-left" target="_self" >--%>
<%--                        <div class="form-group">--%>
<%--                            <label class="control-label" for="search-field"><i class="glyphicon glyphicon-search"></i></label>--%>
<%--                            <input class="form-control search-field" type="search" name="search" id="search-field">--%>
<%--                        </div>--%>
<%--                    </form>--%>
<%--&lt;%&ndash;                    <form action="helloServlet" method="post">&ndash;%&gt;--%>
<%--                        <p class="navbar-text navbar-right"><a class="navbar-link login" href="${pageContext.request.contextPath}/login.jsp">${sessionScope.language['label.Login']}</a>--%>
<%--&lt;%&ndash;                    </form>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <a class="btn btn-default action-button" role="button" href="${pageContext.request.contextPath}/register.jsp">&ndash;%&gt;--%>
<%--&lt;%&ndash;                        ${sessionScope.language['label.Register']}&ndash;%&gt;--%>
<%--&lt;%&ndash;                    </a></p>&ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </nav>--%>
<%--        <div class="container hero">--%>
<%--            <div class="row">--%>
<%--                <div class="col-lg-5 col-lg-offset-1 col-md-6 col-md-offset-0">--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>



<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${sessionScope.language['label.Rental_Car']}</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#">Page 1</a></li>
            <li><a href="#">Page 2</a></li>
            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${sessionScope.language['label.Language']}<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                     <li role="presentation"><a href="?lang=en">${sessionScope.language['header.lang.eng']}</a></li>
                    <li role="presentation"><a href="?lang=uk">${sessionScope.language['header.lang.ukr']}</a></li>
                </ul>
            </li>
        <jsp:include page="/WEB-INF/view/include/sorter.jsp"/>
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
             <li><a href="${pageContext.request.contextPath}/register.jsp"><span class="glyphicon glyphicon-user"></span> ${sessionScope.language['label.Register']}</a></li>
            <li><a href="${pageContext.request.contextPath}/login.jsp"><span class="glyphicon glyphicon-log-in"></span> ${sessionScope.language['label.Login']}</a></li>
        </ul>
    </div>
</nav>




<jsp:include page="/WEB-INF/view/allCars.jsp"/>
<a href="${pageContext.request.contextPath}/index2.jsp"></a>
<out:text/>




<%--<div class="container bootstrap snippets bootdey">--%>
<%--    <div class="btn-demo" id="btn-color-targets">--%>
<%--        <a href="#modalColor" data-target-color="blue" data-toggle="modal" class="btn btn-default ">Blue</a>--%>
<%--        <a href="#modalColor" data-target-color="lightblue" data-toggle="modal" class="btn btn-default ">Light Blue</a>--%>
<%--        <a href="#modalColor" data-target-color="cyan" data-toggle="modal" class="btn btn-default ">Cyan</a>--%>
<%--        <a href="#modalColor" data-target-color="bluegray" data-toggle="modal" class="btn btn-default ">Blue Gray</a>--%>
<%--&lt;%&ndash;        <a href="#modalColor" data-target-color="green" data-toggle="modal" class="btn btn-default ">Green</a>&ndash;%&gt;--%>
<%--        <a href="#modalColor" data-target-color="lightgreen" data-toggle="modal" class="btn btn-default ">Light Green</a>--%>
<%--        <a href="#modalColor" data-target-color="red" data-toggle="modal" class="btn btn-default ">Red</a>--%>
<%--        <a href="#modalColor" data-target-color="amber" data-toggle="modal" class="btn btn-default ">Amber</a>--%>
<%--        <a href="#modalColor" data-target-color="orange" data-toggle="modal" class="btn btn-default ">Orange</a>--%>
<%--        <a href="#modalColor" data-target-color="bluegray" data-toggle="modal" class="btn btn-default ">Blue Gray</a>--%>
<%--        <a href="#modalColor" data-target-color="teal" data-toggle="modal" class="btn btn-default ">Teal</a>--%>
<%--    </div>--%>
<%--</div>--%>

<%--<script>--%>
<%--    $(document).ready(function(){--%>
<%--        $('body').on('click', '#btn-color-targets > .btn', function(){--%>
<%--            var color = $(this).data('target-color');--%>
<%--            $('#modalColor').attr('data-modal-color', color);--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>



</body>
</html>