<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.11/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="webjars/datetimepicker/2.5.1/jquery.datetimepicker.css">
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
                <button class="btn btn-default pull-right" type="submit">Filter</button>
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

            <form method="post" id="detailsForm" class="form-horizontal">
                <div class="modal-body">
                    <input type="hidden" name="id" id="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-sm-2 control-label">DateTime:</label>
                        <div class="col-sm-10">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime">
                        </div>

                    </div>
                    <div class="form-group">
                        <label for="description" class="col-sm-2 control-label">Description:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="description" name="description">
                        </div>

                    </div>
                    <div class="form-group">
                        <label for="calories" class="col-sm-2 control-label">Calories:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="calories" name="calories">
                        </div>

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
<script type="text/javascript" src="webjars/jquery/2.2.3/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.5.1/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.11/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript" src="resources/js/mealsDataTable.js"></script>
</html>
