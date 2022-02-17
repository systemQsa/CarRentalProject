<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<%@ page session="true" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session"/>

<html>
<head >
    <%--    <meta http-equiv='cache-control' content='no-cache'>--%>
    <%--    <meta http-equiv='expires' content='0'>--%>
    <%--    <meta http-equiv='pragma' content='no-cache'>--%>
    <title>Login</title>
    <title>JSP - Hello World</title>
    <!-- Bootstrap CSS (Cloudflare CDN) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">
    <!-- jQuery (Cloudflare CDN) -->
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <!-- Bootstrap Bundle JS (Cloudflare CDN) -->
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<h1>LOGIN</h1>
<c:out value="${pageContext.request.requestURL}"/>

<div class="col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">
    <div class="bg-light p-5 border shadow">
        <!-- Login Form -->
        <form method="post" action="helloServlet" name="login">
            <input type="hidden" name="action" value="login">
            <h4>${language['label.Login']}</h4>
            <div class="mb-4">
                <input  name="login" type="text" placeholder="${language['label.login']}">
                <p class="form-text text-end">${language['label.Enter']}${language['label.username']}</p>
            </div>
            <div class="mb-4">
                <input name="password" type="password" class="form-control" placeholder="${language['label.password']}">
                <p class="form-text text-end"> ${language['label.Enter']}${language['label.email']}</p>
            </div>
            <div class="mb-4 form-check w-100">
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input"> ${language['label.remember_me']}
                </label>
                <a href="#" class="float-end">${language['label.reset_password']}</a>
            </div>
            <button type="submit" class="btn btn-primary w-100 my-3 shadow" >${language['label.Login']}</button>
            <p class="text-center m-0">Not yet account,<a href="${pageContext.request.contextPath}/register.jsp">${language['label.register']}</a></p>
        </form>
    </div>
</div>
</body>
</html>
