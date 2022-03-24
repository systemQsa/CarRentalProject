<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<html>
<head >
       <title>Login</title>
    <title>JSP - Hello World</title>
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">
     <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
     <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<jsp:include page="/WEB-INF/view/errorMSG.jsp"/>

<div class="col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">
    <div class="bg-light p-5 border shadow">
        <h3>${sessionScope.language['label.Login']}</h3>
        <!-- Login Form -->
        <form method="post" action="helloServlet" name="login">
            <input type="hidden" name="action" value="login">
            <div class="mb-4">
                <input  name="login" type="text" placeholder="${sessionScope.language['label.login']}">
                <p class="form-text text-end">${sessionScope.language['label.Enter']}${sessionScope.language['label.username']}</p>

            </div>
            <div class="mb-4">
                <input name="password" type="password" class="form-control" placeholder="${sessionScope.language['label.password']}">
                <p class="form-text text-end"> ${sessionScope.language['label.Enter']}${sessionScope.language['label.email']}</p>
<%--                <c:if test="${requestScope.err == 3}">--%>
<%--                    <div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
<%--                        <strong>Error!</strong> ${requestScope.errMSG}--%>
<%--                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                            <span aria-hidden="true">&times;</span>--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                </c:if>--%>

            </div>
            <div class="mb-4 form-check w-100">
                <p class="text-center">
                    <a href="${pageContext.request.contextPath}/forgotPass.jsp" class="float-end">${sessionScope.language['forgot_password']}?</a>
                 </p>
             </div>
            <button type="submit" class="btn btn-primary w-100 my-3 shadow" >${sessionScope.language['label.Login']}</button>
            <p class="text-center m-0">${sessionScope.language['not_have_account_yet']},<a href="${pageContext.request.contextPath}/register.jsp">${sessionScope.language['label.register']}</a></p>
        </form>
    </div>
</div>

<a href="${pageContext.request.contextPath}/index.jsp">${sessionScope.language['Go_back']}</a>

</body>
</html>