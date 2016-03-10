<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meal list</title>

    <jsp:include page="resources/fragments/header.jsp" />
</head>
<body>
    <div class="container">
        <a href="meals?action=create" class="btn btn-primary pull-right">Create</a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th></th>
                </tr>
            </thead>

            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMealWithExceed" scope="page"/>
                <tr class="${meal.exceed ? 'red' : 'green'}">
                    <td><%= TimeUtil.toString(meal.getDateTime()) %></td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>
                        <a href="meals?action=delete&id=${meal.id}" class="btn btn-danger pull-right left-margin">Delete</a>
                        <a href="meals?action=update&id=${meal.id}" class="btn btn-success pull-right">Update</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
