<%@ page import="com.ek.serialsserver.tvseries.routes.TVSeriesRoutes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/static/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="row">
        <a href="<%=TVSeriesRoutes.ADD%>" class="btn btn-success">Create tv series</a>
    </div>
    <div class="row">
        <table class="table">
            <thead>
            <tr>
                <th>Title</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tv_series}" var="tv">
                <tr>
                    <td><a href="/tv/${tv.id}">${tv.title}</a></td>
                    <td>${tv.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
