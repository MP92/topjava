<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meal list</title>

    <jsp:include page="resources/fragments/header.jsp" />
</head>
<body>
    <div class="container">
        <form method="post">
            <button formaction="meals-create" class="btn btn-primary pull-right">Create</button>

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th></th>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Calories</th>
                    </tr>
                </thead>

                <c:forEach items="${userMeals}" var="meal">
                    <tr class="${meal.exceed ? 'red' : 'green'}">
                        <td><input type="radio" name="id" value="${meal.id}"/></td>
                        <td>${meal.formattedDateTime}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                    </tr>
                </c:forEach>
            </table>
            <button class="btn btn-danger pull-right left-margin" formaction="meals-delete">Delete</button>
            <button class="btn btn-success pull-right" formaction="meals-edit">Update</button>
        </form>
    </div>
</body>
</html>
