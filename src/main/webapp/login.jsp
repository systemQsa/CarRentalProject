<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<%--<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session"/>--%>
<%--<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"/>--%>
<%--<fmt:setLocale value="${lang}"/>--%>
<%--<fmt:setBundle basename="resources" var="locale"/>--%>
<html>
<head >
       <title>Login</title>
    <title>JSP - Hello World</title>
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">
     <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
     <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<h1>LOGIN</h1>
<c:if test="${requestScope.err == 1}">
    <p class="text-center">${requestScope.errMSG}</p>
</c:if>

<div class="col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">
    <div class="bg-light p-5 border shadow">
        <h3>${pageContext.request.locale}</h3>
        <!-- Login Form -->
        <form method="post" action="helloServlet" name="login">
            <input type="hidden" name="action" value="login">
<%--            <h4><fmt:message bundle="${locale}" key="label.Login"/></h4>--%>
            <div class="mb-4">
                <input  name="login" type="text" placeholder="${sessionScope.language['label.login']}">
                <p class="form-text text-end">${sessionScope.language['label.Enter']}${sessionScope.language['label.username']}</p>
                <c:if test="${requestScope.err == 2}">
                    <p>${requestScope.errMSG}</p>
                </c:if>

            </div>
            <div class="mb-4">
                <input name="password" type="password" class="form-control" placeholder="${sessionScope.language['label.password']}">
                <p class="form-text text-end"> ${sessionScope.language['label.Enter']}${sessionScope.language['label.email']}</p>
                <c:if test="${requestScope.err == 3}">
                    <p>${requestScope.errMSG}</p>
                </c:if>

            </div>
            <div class="mb-4 form-check w-100">
                <%--                <label class="form-check-label">--%>
                <%--                    <input type="checkbox" class="form-check-input"> ${language['label.remember_me']}--%>
                <%--                </label>--%>
                <a href="#" class="float-end">FORGOT PASSWORD</a>
            </div>
            <button type="submit" class="btn btn-primary w-100 my-3 shadow" >${sessionScope.language['label.Login']}</button>
            <p class="text-center m-0">Not yet account,<a href="${pageContext.request.contextPath}/register.jsp">${sessionScope.language['label.register']}</a></p>
        </form>
    </div>
</div>


</body>
</html>