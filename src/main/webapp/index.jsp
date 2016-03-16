<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Top Java.</title>
</head>
<body>
    <h2>Проект "<a href="https://github.com/JavaWebinar/topjava06" target="_blank">Top Java"</a></h2>
    <hr>
    <form action="users" method="post">
        <label>
            User id:
            <select name="id">
                <option>0</option>
                <option>1</option>
            </select>
        </label>
        <button type="submit">Select</button>
        <c:if test="${!empty userSelectedMsg}">
            <span style="color: green">${userSelectedMsg}</span>
        </c:if>
    </form>
    <ul>
        <li><a href="users">User List</a></li>
        <li><a href="meals">Meal List</a></li>
    </ul>
</body>
</html>
