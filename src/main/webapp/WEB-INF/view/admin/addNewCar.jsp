<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>ADD NEW CAR</h1>
<form  method="post" action="${pageContext.request.contentType}/car/helloServlet">
    <input type="hidden" name="action" value="addCar">
    <label>CAR NAME</label>
    <input type="text" name="name">
    <label>CAR CLASS</label>
    <input type="text" name="carClass">
    <label>CAR BRAND</label>
    <input type="text" name="brand">
    <label>CAR RENTAL PRICE</label>
    <input type="text" name="rentalPrice">
    <label>CAR PHOTO</label>
    <input type="image" name="photo">
    <button type="submit">ADD CAR</button>
</form>

</body>
</html>
