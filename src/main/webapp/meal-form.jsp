<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meal Form</title>
    <jsp:include page="resources/fragments/header.jsp" />
</head>
<body>
    <c:if test="${!empty meal}">
        <c:set var="description" value="${meal.description}"/>
        <c:set var="dateTime" value="${meal.dateTime}"/>
        <c:set var="calories" value="${meal.calories}"/>
        <c:set var="id" value="${meal.id}"/>
    </c:if>
    <c:if test="${empty meal}">
        <c:set var="description" value=""/>
        <c:set var="dateTime" value=""/>
        <c:set var="calories" value=""/>
        <c:set var="id" value=""/>
    </c:if>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <form method="post" data-toggle="validator" class="form-horizontal">
                    <input type="hidden" name="id" value="${id}"/>
                    <div class="form-group">
                        <label for="description" class="control-label col-sm-3">Description</label>
                        <div class="col-sm-9">
                            <input type="text" name="description" class="form-control" id="description"
                                   placeholder="Description" value="${description}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calories" class="col-sm-3 control-label">Calories</label>
                        <div class="col-sm-9">
                            <input type="number" name="calories" id="calories" min="1" max="100000"
                                   class="form-control" value="${calories}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dateTime" class="col-sm-3 control-label">DateTime</label>
                        <div class="col-sm-9">
                            <input type="datetime-local" name="dateTime" id="dateTime" value="${dateTime}"
                                   class="form-control" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-default">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
