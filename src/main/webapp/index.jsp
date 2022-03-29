<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="out" uri="mytag" %>
<!DOCTYPE html>
<html>
<link>
    <meta>
    <title>${sessionScope.language['label.Rental_Car']}</title>
 <%--    <style><%@include file="css/color.css"%></style>--%>
<%--  <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/css/index.css"/>">--%>
</head>
<body>
<jsp:include page="/WEB-INF/view/include/header.jsp"/>

 <nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${sessionScope.language['label.Rental_Car']}</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="${pageContext.request.contextPath}/index.jsp">${sessionScope.language['Home']}</a></li>
            <li><a href="#">${sessionScope.language['page']} 1</a></li>
            <li><a href="#">${sessionScope.language['page']} 2</a></li>
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


<div class="container">
    <jsp:include page="/WEB-INF/view/allCars.jsp"/>
</div>
<div class="navbar fixed-bottom">
    <jsp:include page="WEB-INF/view/include/footer.jsp"/>
</div>
<%--<a href="${pageContext.request.contextPath}/index2.jsp"></a>--%>





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
<%--<jsp:include page="WEB-INF/view/include/footer.jsp"/>--%>


</body>
</html>
