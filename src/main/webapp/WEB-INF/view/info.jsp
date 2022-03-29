<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<div class="container">
    <div class="h-100 row align-items-center">
        <div class="col">
            <c:if test="${not empty sessionScope.info_num}">
                <div class="alert alert-success alert-dismissible text-center">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;
                        ${sessionScope.remove("info_num")}
                    </a>
                    <span class="text-center">
                      <strong>Info!</strong><br> ${sessionScope.info}
                    </span>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
