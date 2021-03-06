<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>${sessionScope.language['New_Password']}</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
</head>
<body>

<a href="${pageContext.request.contextPath}/forgotPass.jsp" role="button" class="btn btn-info btn-sm">${sessionScope.language['Go_back']}</a>
<jsp:include page="WEB-INF/view/errorMSG.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="text-center">
                        <h3><i class="fa fa-lock fa-4x"></i></h3>
                        <h2 class="text-center">${sessionScope.language['enter_new_password']}</h2>
                        <div class="panel-body">
                             <form id="register-form" role="form" autocomplete="off" class="form" method="post" action="helloServlet">
                                <input type="hidden" name="action" value="activateNewPassword">
                                <input type="hidden" name="userLogin" value="${sessionScope.userLogin}">
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>
                                        <input id="password" name="password" placeholder="${sessionScope.language['new_password']}" class="form-control"  type="password">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input name="recover-submit" class="btn btn-lg btn-primary btn-block" value="${sessionScope.language['change_password']}" type="submit"
                                           data-target="#editMezalta" data-toggle="modal">
                                </div>

                                <input type="hidden" class="hide" name="token" id="token" value="">
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
