<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>${sessionScope.language['forgot_password']}</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">


    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
 </head>
<body>
<div>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-info" role="button">${sessionScope.language['Go_back']}</a>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4" style="margin-top: 10%">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="text-center">
                        <h3><i class="fa fa-lock fa-4x"></i></h3>
                        <h2 class="text-center">${sessionScope.language['change_pass']}?</h2>
                        <p>${sessionScope.language['you_can_reset_your_password_here']}.</p>
                        <c:if test="${not empty requestScope.err}">
                                <div class="alert alert-danger alert-dismissible">
                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                    <span class="text-center">
                                         <strong>${sessionScope.language['error']}!</strong> ${requestScope.errMSG}
                                    </span>
                                </div>
                        </c:if>
                        <div class="panel-body">
                             <form id="register-form" role="form" autocomplete="off" class="form" method="post" action="helloServlet">
                                <input type="hidden" name="action" value="forgotPass">
                                <input type="hidden" name="setCommand" value="checkUser">
                                 <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>
                                        <input id="email" name="login" placeholder="${sessionScope.language['email_address']}"
                                               class="form-control"  type="email">
                                    </div>
                                </div>
                                <div class="form-group">
<%--                                    check if reset password need to put in value ??--%>
                                    <input name="recover-submit" class="btn btn-lg btn-primary btn-block" value="Reset Password"
                                           type="submit"
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

<%--this modal window opens when secret code were sent to user email--%>
<div class="modal fade" id="editMezalta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">${sessionScope.language['reset_password']}</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="helloServlet">
                    <input type="hidden" name="action" value="forgotPass">
                    <input type="hidden" name="setCommand" value="checkSecretCode">
                    <div class="form-group">
                        <h6>${sessionScope.language['inform_that_secret_code_was_sent_to_user_email']}!</h6>
                     </div>
                    <input type="text" class="form-control"  name="secretNumbers">
                    <button class="btn btn-primary btn-sm" type="submit">${sessionScope.language['Send']}</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">${sessionScope.language['Close']}</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
