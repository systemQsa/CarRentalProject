<%@ page import="com.myproject.dao.connection.ConnectionPool" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connection</title>
</head>
<body>

<h1>
    <%
        System.out.println(ConnectionPool.getInstance().getConnection());
    %>
</h1>

</body>
</html>
