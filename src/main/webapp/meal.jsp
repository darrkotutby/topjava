<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>

<c:set var="edited" value="Meal id=${meal.id}"/>
<c:set var="title" scope="request" value="${meal.id == 0 ? 'New user' : edited}"/>

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
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Date/Time*</dt>
            <dd><input type="text" name="dateTime" size=50
                       value="${meal.dateTime.format(TimeUtil.getFormatter())}" ${meal.id > 0 ? 'readonly' : ''}
                       required
                       pattern="(0[1-9]|[12][0-9]|3[01])[- -.](0[1-9]|1[012])[- -.](19|20|30)\d\d[- - ](0[0-9]|1[0-9]|2[0-4])[- -:]([0-5][0-9])[- -:]([0-5][0-9])"
                       title="DD.MM.YYYY HH:MI:SS"></dd>
        </dl>
        <dl>
            <dt>Description</dt>
            <dd><input type="text" name="description" size=50 value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt>Calories*</dt>
            <dd><input type="number" name="calories" size=50 value="${meal.calories}" min="0" required></dd>
        </dl>
        <button type="submit" name="save" value="1">Save</button>
        <button type="button" onclick="window.history.back()" name="CancelEdit" value="1">Cancel</button>
    </form>


</section>
</body>
</html>