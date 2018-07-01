<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="edited" value="User id=${user.id}"/>
<c:set var="title" scope="request" value="${user.id == 0 ? 'New user' : edited}"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>${title}</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${title}</h2>
<section>
    <form method="post" action="users" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${user.id}">
        <dl>
            <dt>Login*</dt>
            <dd><input type="text" name="login" size=50 value="${user.login}" required></dd>
        </dl>
        <dl>
            <dt>Full name</dt>
            <dd><input type="text" name="fullName" size=50 value="${user.fullName}"></dd>
        </dl>
        <dl>
            <dt>Calories per date*</dt>
            <dd><input type="number" name="caloriesPerDate" size=50 value="${user.caloriesPerDate}" min="0" required>
            </dd>
        </dl>
        <button type="submit" name="save" value="1">Save</button>
        <button onclick="window.history.back()" name="CancelEdit" value="1">Cancel</button>
    </form>
</section>
</body>
</html>