<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
      <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>

<%--    <c:if test="${not empty requestScope.err}">--%>
<%--         <div class="alert alert-danger alert-dismissible col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">--%>
<%--            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>--%>
<%--            <span class="text-center">--%>
<%--                <strong>Error!</strong> ${requestScope.errMSG}--%>
<%--            </span>--%>
<%--        </div>--%>
<%--    </c:if>--%>


<%--<c:if test="${requestScope.err == 2}">--%>
<%--    <c:set var="MS" value="${sessionScope.language['err.login']}"/>--%>
<%--    <c:out value="${MS}"/>--%>
<%--</c:if>--%>

<%--    <c:if test="${requestScope.err == 3}">--%>
<%--        <c:set var="MSG" value="${sessionScope.language['err.password']}"/>--%>
<%--    </c:if>--%>

    <c:if test="${not empty requestScope.err}">
        <div class="alert alert-danger alert-dismissible col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <span class="text-center">
                <strong>${sessionScope.language['error']}!</strong>
                 ${requestScope.errMSG}
            </span>
        </div>
    </c:if>


</body>
</html>
