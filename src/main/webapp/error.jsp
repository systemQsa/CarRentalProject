<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>

<%--<c:if test="${ not empty pageContext.exception.message}">--%>
<%--    <h2>${pageContext.exception.message}</h2>--%>

<%--</c:if>--%>
<%--<c:if test="${ not empty pageContext.exception.localizedMessage}">--%>
<%--    <h3>${pageContext.exception.localizedMessage}</h3>--%>
<%--</c:if>--%>

<%--<c:if test="${ not empty pageContext.exception.stackTrace}">--%>
<%--    <h3>${pageContext.exception.stackTrace}</h3>--%>
<%--</c:if>--%>

<%--<c:out value="${pageContext.request.requestURL}"/>--%>

<%--<img  class="text-center" alt="error page" src="<c:url value="png/404.png" />" style="width: 60%; height: 70%;"/>--%>


 <div class="container">
    <div class="col-6 offset-md-3">
        <img src="http://lamcdn.net/lookatme.ru/post_image-image/sIaRmaFSMfrw8QJIBAa8mA-small.png" alt="404" />
    </div>
</div>


</body>
</html>
