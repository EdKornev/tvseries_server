<%@ page import="com.ek.serialsserver.tvseries.routes.TVSeriesRoutes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eduard
  Date: 04.05.2016
  Time: 1:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="<%=TVSeriesRoutes.ADD%>" method="post">
        <input name="title" placeholder="Title"/>
        <input name="description" placeholder="Description"/>
        <input type="submit" value="Add"/>
    </form>
    <table>
        <thead>
            <tr>
                <th>Заголовок</th>
                <th>Описание</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${tv_series}" var="tv">
            <tr>
                <td>${tv.title}</td>
                <td>${tv.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
