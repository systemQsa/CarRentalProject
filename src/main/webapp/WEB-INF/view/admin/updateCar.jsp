<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Update Car</title>
</head>
<body>
<h1>UPDATE CAR</h1>


    <h3>Hello</h3>
<%--    <c:out value="${requestScope.carName}"/>--%>
<%--    <h4><%=application.getAttribute("carName")%></h4>--%>


<h4>${sessionScope.get("carName")}</h4>

<h3>Hello</h3>


<form method="post" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="updateCar">
    <input type="hidden" name="carId" value="${sessionScope.carId}">

    <label>Car Name  ${sessionScope.carName}</label>
    <input type="text" name="carName"  value="${sessionScope.get("carName")}">

    <label>Car Class  ${sessionScope.carClass}</label>
    <input type="text"  name="carClass" value="${sessionScope.carClass}">

    <label>Car brand  ${sessionScope.brand}</label>
    <input type="text" capture="brand" value="${sessionScope.brand}">

    <label>Car Rental price  ${sessionScope.rentalPrice}</label>
    <input type="text"  name="rentalPrice" value="${sessionScope.rentalPrice}">

    <label>Car photo  ${sessionScope.carPhoto}</label>
    <input type="text" name="carPhoto" value="${sessionScope.carPhoto}">
    <button type="submit">UPDATE</button>
</form>

</body>
</html>
