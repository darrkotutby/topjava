<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Users</h2>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>List of users</title>
    <style>
        td {
            border: 1px solid #808080;
        }

        th {
            border: 1px solid #808080;
        }
    </style>
</head>
<body>
<section>
    <table cellpadding="8" cellspacing="0" align=center>
        <tr>
            <td style="border: 0px solid #808080;">
                <a href="users?action=new"><img src="img/add.png"> Add new user</a>
            </td>
        </tr>
        <tr>
            <th>Id</th>
            <th>Login</th>
            <th>Full name</th>
            <th>Calories per date</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.fullName}</td>
                <td>${user.caloriesPerDate}</td>
                <td><a href="users?id=${user.id}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="users?id=${user.id}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>