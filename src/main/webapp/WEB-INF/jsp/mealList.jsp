<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="resources/js/mealsDataTable.js"></script>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <%--http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page--%>
        <h3><a class="btn btn-default" href="${pageContext.request.contextPath}">Home</a></h3>
        <h3><fmt:message key="meals.title"/></h3>
        <div class="well row">
            <form method="get" class="form-inline" id="filter-form">
                <div class="form-group">
                    <label for="startDate">From Date:</label>
                    <input type="date" class="form-control" name="startDate" id="startDate" value="${startDate}">
                </div>
                <div class="form-group">
                    <label for="endDate">To Date:</label>
                    <input type="date" class="form-control" name="endDate" id="endDate" value="${endDate}">
                </div>
                <div class="form-group">
                    <label for="startTime">From Time:</label>
                    <input type="time" class="form-control" name="startTime" id="startTime" value="${startTime}">
                </div>
                <div class="form-group">
                    <label for="endTime">To Time:</label>
                    <input type="time" class="form-control" name="endTime" id="endTime" value="${endTime}">
                </div>
                <button class="btn btn-default" type="submit" style="float: right">Filter</button>
            </form>
        </div>

        <a id="add" class="btn btn-sm btn-info"><fmt:message key="meals.add"/></a>

        <table class="table table-striped" id="mealsTable">
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
                <tr class="${meal.exceed ? 'exceeded' : 'normal'}" id="${meal.id}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%=TimeUtil.toString(meal.getDateTime())%>
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a class="btn btn-xs btn-primary edit">Edit</a></td>
                    <td><a class="btn btn-xs btn-danger delete">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow" tabindex="-1" role="dialog" aria-labelledby="editRowLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editRowLabel"><fmt:message key="meals.edit"/></h4>
            </div>

            <form method="post" id="detailsForm">
                <div class="modal-body">
                    <input type="hidden" name="id" id="id">

                    <div class="form-group">
                        <label for="dateTime">DateTime:</label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime">
                    </div>
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <input type="text" class="form-control" id="description" name="description">
                    </div>
                    <div class="form-group">
                        <label for="calories">Calories:</label>
                        <input type="number" class="form-control" id="calories" name="calories">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
