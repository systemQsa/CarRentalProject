<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="resources" var="locale"/>
<html>
<head>
    <title>Title</title>
    <%--    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">--%>
    <%--    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">--%>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <%--    <link rel="stylesheet" type="text/css" href="<c:url value='/css/header.css'/>"/>--%>
</head>
<body>


<%--<ul>--%>
<%--    <li>--%>
<%--        <div class="dropdown">--%>
<%--            <form action="${pageContext.request.contentType}/car/helloServlet" method="post">--%>
<%--                <input type="hidden" name="action" value="switchLang">--%>
<%--                <input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">--%>
<%--                <input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">--%>
<%--                <div class="dropdown-content">--%>
<%--                    <button type="submit" name="language" value="uk">--%>
<%--                        <fmt:message bundle="${locale}" key="header.lang.ukr"/>--%>
<%--                    </button>--%>
<%--                    <p>LoCALe request</p>--%>
<%--                    <p>"${pageContext.request.locale}"</p>--%>
<%--                    <p> Locale response</p>--%>
<%--                    <p>${pageContext.response.locale}"</p>--%>
<%--                    <button type="submit" name="language" value="en">--%>
<%--                        <fmt:message bundle="${locale}" key="header.lang.eng"/>--%>
<%--                    </button>--%>
<%--                </div>--%>
<%--            </form>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--</ul>--%>

</body>
</html>

