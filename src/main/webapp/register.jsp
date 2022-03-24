<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="WEB-INF/view/errorMSG.jsp"/>

<%--<c:if test="${requestScope.err == 8}">--%>
<%--    <div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
<%--        <strong class="text-center">Error!</strong> ${requestScope.errMSG}--%>
<%--        <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--            <span aria-hidden="true">&times;</span>--%>
<%--        </button>--%>
<%--    </div>--%>
<%--</c:if>--%>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card">
                <div class="card-header">${sessionScope.language['label.Register']}</div>
                <div class="card-body">

                    <form class="form-horizontal" method="post" action="helloServlet">
                        <input type="hidden" name="action" value="register">
                        <div class="form-group">
                            <label for="name"
                                   class="cols-sm-2 control-label">${sessionScope.language['label.Name']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="name" id="name"
                                           placeholder="${sessionScope.language['label.Enter']}${sessionScope.language['label.Name']}"/>
<%--                                    <c:if test="${requestScope.err == 4}">--%>
<%--                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
<%--                                            <strong>Error!</strong> ${requestScope.errMSG}--%>
<%--                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                                                <span aria-hidden="true">&times;</span>--%>
<%--                                            </button>--%>
<%--                                        </div>--%>
<%--                                    </c:if>--%>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email"
                                   class="cols-sm-2 control-label">${sessionScope.language['label.Surname']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="surname" id="email"
                                           placeholder="${sessionScope.language['label.Surname']}"/>
<%--                                    <c:if test="${requestScope.err == 4}">--%>
<%--                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
<%--                                            <strong>Error!</strong> ${requestScope.errMSG}--%>
<%--                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                                                <span aria-hidden="true">&times;</span>--%>
<%--                                            </button>--%>
<%--                                        </div>--%>
<%--                                    </c:if>--%>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="username"
                                       class="cols-sm-2 control-label"> ${sessionScope.language['label.Phone']}+(380)</label>
                                <div class="cols-sm-10">
                                    <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                        <input type="text" class="form-control" name="phone"
                                               placeholder="${sessionScope.language['label.Phone']}"/>
<%--                                        <c:if test="${requestScope.err == 7}">--%>
<%--                                            <div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
<%--                                                <strong>Error!</strong> ${requestScope.errMSG}--%>
<%--                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                                                    <span aria-hidden="true">&times;</span>--%>
<%--                                                </button>--%>
<%--                                            </div>--%>
<%--                                        </c:if>--%>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="username"
                                   class="cols-sm-2 control-label"> ${sessionScope.language['label.Username']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="login" id="username"
                                           placeholder="${sessionScope.language['label.username']}"/>
<%--                                    <c:if test="${requestScope.err == 5}">--%>
<%--                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
<%--                                            <strong>Error!</strong> ${requestScope.errMSG}--%>
<%--                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                                                <span aria-hidden="true">&times;</span>--%>
<%--                                            </button>--%>
<%--                                        </div>--%>
<%--                                    </c:if>--%>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password"
                                   class="cols-sm-2 control-label">${sessionScope.language['label.Password']}</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" name="password" id="password"
                                           placeholder="${sessionScope.language['label.Enter']}${sessionScope.language['label.password']}"/>
<%--                                    <c:if test="${requestScope.err == 6}">--%>
<%--                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
<%--                                            <strong>Error!</strong> ${requestScope.errMSG}--%>
<%--                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                                                <span aria-hidden="true">&times;</span>--%>
<%--                                            </button>--%>
<%--                                        </div>--%>
<%--                                    </c:if>--%>
                                </div>
                            </div>
                        </div>

                        <div class="form-group ">
                            <button type="submit"
                                    class="btn btn-primary btn-lg btn-block login-button">${sessionScope.language['label.Register']}</button>
                        </div>
                        <div class="login-register text-center">
                            <a href="${pageContext.request.contextPath}/login.jsp">${sessionScope.language['label.Login']}</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<a href="${pageContext.request.contextPath}/index.jsp">${sessionScope.language['Go_back']}</a>
</body>
</html>