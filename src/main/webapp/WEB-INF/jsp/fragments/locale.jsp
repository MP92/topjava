<%@page contentType="text/html" pageEncoding="UTF-8" %>
<ul class="nav navbar-nav">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">${pageContext.response.locale} <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a class="locale-option" data-locale="en">English</a></li>
            <li><a class="locale-option" data-locale="ru">Русский</a></li>
        </ul>
    </li>
</ul>
