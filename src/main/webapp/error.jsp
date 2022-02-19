<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h1> OOPS Error page</h1>

<c:if test="${ not empty pageContext.exception.message}">
    <h2>${pageContext.exception.message}</h2>

</c:if>
<c:if test="${ not empty pageContext.exception.message}">
    <h3>${pageContext.exception.message}</h3>
</c:if>

<c:out value="${pageContext.request.requestURL}"/>
<c:if test="${ not empty sessionScope.errorMSG}">
    <p>${sessionScope.errorMSG}</p>
</c:if>



</body>
</html>
