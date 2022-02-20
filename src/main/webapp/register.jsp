<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"
       scope="session"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="resources" var="locale"/>
<html>
<head lang="${lang}">
    <meta http-equiv='cache-control' content='no-cache'>
    <meta http-equiv='expires' content='0'>
    <meta http-equiv='pragma' content='no-cache'>
    <title>Register</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<h1>REGISTER</h1>
<h3><fmt:message bundle="${locale}" key="label.Rental_Car"/></h3>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card">
                <div class="card-header">${language['label.Register']}</div>
                <div class="card-body">

                    <form class="form-horizontal" method="post" action="helloServlet">
                        <input type="hidden" name="action" value="register">
                        <div class="form-group">
                            <label for="name" class="cols-sm-2 control-label">${language['label.Name']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="name" id="name"
                                           placeholder="${language['label.Enter']}${language['label.Name']}"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="cols-sm-2 control-label">${language['label.Surname']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="surname" id="email"
                                           placeholder="${language['label.Surname']}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="username" class="cols-sm-2 control-label"> ${language['label.Phone']}+(380)</label>
                                <div class="cols-sm-10">
                                    <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                        <input type="text" class="form-control" name="phone"
                                               placeholder="${language['label.Phone']}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="username" class="cols-sm-2 control-label"> ${language['label.Username']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="login" id="username"
                                           placeholder="${language['label.username']}"/>
                                    <%--                                    <c:if test="${not empty pageContext.errorData.statusCode != 0}">--%>
                                    <%--                                        <p>${requestScope.errorLogin}</p>--%>
                                    <%--                                    </c:if>--%>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="cols-sm-2 control-label">${language['label.Password']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" name="password" id="password"
                                           placeholder="${language['label.Enter']}${language['label.password']}"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="confirm"
                                   class="cols-sm-2 control-label">${language['label.Confirm']}${language['label.password']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" name="confirm" id="confirm"
                                           placeholder="${language['label.Confirm']}${language['label.password']}"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group ">
                            <button type="submit"
                                    class="btn btn-primary btn-lg btn-block login-button">${language['label.Register']}</button>
                        </div>
                        <div class="login-register">
                            <a href="${pageContext.request.contextPath}/login.jsp">${language['label.Login']}</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

<h1><fmt:message  bundle="${locale}" key="label.WELCOME"/>
    <h2>
        <fmt:message bundle="${locale}" key="label.Login"/>
    </h2>
    <h2>
        <fmt:message bundle="${locale}" key="label.register"/>
    </h2>

    <h1> <fmt:message bundle="${locale}" key="label.WELCOME"/> </h1>
</body>
</html>