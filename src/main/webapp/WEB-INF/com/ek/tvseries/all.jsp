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
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tv_series}" var="tv">
                <tr>
                    <td><a href="/tv/${tv.id}">${tv.title}</a></td>
                    <td>${tv.description}</td>
                    <td>
                        <form action="<%=TVSeriesRoutes.REMOVE%>" method="post">
                            <input type="hidden" name="id" value="${tv.id}"/>
                            <button class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Remove
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
