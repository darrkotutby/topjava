<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>

<section>

    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <hr/>
    <form method="get" action="meals">
        <dl>
            <dt>Date From:</dt>
            <dd><input type="date" name="dateFrom" id="dateFrom"></dd>
        </dl>
        <dl>
            <dt>Date To:</dt>
            <dd><input type="date" name="dateTo" id="dateTo"></dd>
        </dl>
        <dl>
            <dt>Time From:</dt>
            <dd><input type="time" name="timeFrom" id="timeFrom"></dd>
        </dl>
        <dl>
            <dt>Time To:</dt>
            <dd><input type="time" name="timeTo" id="timeTo"></dd>
        </dl>
        <button type="submit">Filter</button>
        <button onclick="javascript: {document.getElementById('dateFrom').value = '';
                                      document.getElementById('dateTo').value = '';
                                      document.getElementById('timeFrom').value = '';
                                      document.getElementById('timeTo').value = '';}" type="button">Clear
        </button>
    </form>
    <hr/>
    <a href="meals?action=create">Add Meal</a>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>