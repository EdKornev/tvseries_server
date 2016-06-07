<%@ page import="com.ek.serialsserver.tvseries.routes.TVSeriesRoutes" %>
<%@ page import="com.ek.serialsserver.index.routes.IndexRoutes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="<%=IndexRoutes.getRoute("/static/css/bootstrap.css")%>" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="row">
        <h1>All tv shows</h1>
    </div>
    <div class="row">
        <c:url value="<%=TVSeriesRoutes.PARSE%>" var="parseUrl"/>
        <form action="${parseUrl}" method="post">
            <div class="form-group">
                <input class="form-control" name="url" placeholder="Url" style="width: 20%; float: left; margin-right: 15px;"/>
                <input type="submit" class="btn btn-default" value="Parse"/>
            </div>
        </form>
    </div>
    <div class="row">
        <a href="<%=IndexRoutes.getRoute(TVSeriesRoutes.ADD)%>" class="btn btn-success">Create tv series</a>
    </div>
    <div class="row">
        <table class="table">
            <thead>
            <tr>
                <th style="width: 30%;">Title</th>
                <th style="width: 55%;">Description</th>
                <th style="width: 15%;"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tv_series}" var="tv">
                <tr>
                    <td style="width: 30%;"><a href="<%=IndexRoutes.getRoute("/tv/")%>${tv.id}">${tv.title}</a></td>
                    <td style="width: 55%;">${tv.description}</td>
                    <td style="width: 15%;">
                        <form action="<%=IndexRoutes.getRoute(TVSeriesRoutes.REMOVE)%>" method="post">
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
