<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <jsp:include page="resources/fragments/header.jsp" />
</head>
<body>
    <div class="container">
        <div class="alert alert-warning">
            ${errorMsg}
        </div>
    </div>
</body>
</html>
