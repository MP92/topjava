<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <jsp:useBean id="loggedUser" scope="session" class="ru.javawebinar.topjava.LoggedUser"/>

    <h2><a href="index.jsp">Home</a></h2>
    <h1>User id: ${loggedUser.id}</h1>
    <h3>Meal list</h3>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <form action="meals" method="get">
        <input type="hidden" name="action" value="filter">

        <div style="display:inline-block; width:25%;">
            <label>Start date: <input name="startDate" type="date" value="${startDate}"></label><br>
            <label>Start time: <input name="startTime" type="time" value="${startTime}"></label>

        </div>
        <div style="display:inline-block; width:25%; margin-left:10%">
            <label>End date: <input name="endDate" type="date" value="${endDate}"></label><br>
            <label>End time: <input name="endTime" type="time" value="${endTime}"></label>
        </div>
        <br>
        <form-group>
            <button type="submit">Filter</button>
        </form-group>
    </form>
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
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%=DateTimeUtil.toString(meal.getDateTime())%>
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