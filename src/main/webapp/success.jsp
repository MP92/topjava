<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Success</title>

    <jsp:include page="resources/fragments/header.jsp" />
</head>
<body>
    <c:if test="${!empty message}">
        <div class="container">
            <div class="alert alert-info">${message}</div>
        </div>
    </c:if>
</body>
</html>
