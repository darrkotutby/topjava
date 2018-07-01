<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>

<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>List of meals</title>
    <style>
        td {
            border: 1px solid #808080;
        }

        th {
            border: 1px solid #808080;
        }

        tr[data-mealExceed="false"] {
            color: green;
        }

        tr[data-mealExceed="true"] {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <table cellpadding="8" cellspacing="0" align=center>
        <tr>
            <td style="border: 0 solid #808080;">
                <a href="meals?action=new"><img src="img/add.png"> Add new meal</a>
            </td>
        </tr>
        <tr>
            <th>Id</th>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr data-mealExceed=${meal.exceed}>
                <td>${meal.id}</td>
                <td>${meal.dateTime.format(TimeUtil.getFormatter())}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="meals?id=${meal.id}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>