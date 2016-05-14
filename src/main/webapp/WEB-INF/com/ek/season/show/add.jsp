<%@ page import="com.ek.serialsserver.season.routes.SeasonRoutes" %>
<%@ page import="com.ek.serialsserver.index.routes.IndexRoutes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%=IndexRoutes.getRoute("/static/css/bootstrap.css")%>" rel="stylesheet"/>
    <title>Add show</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>Add show</h1>
        </div>
    </div>
    <form action="<%=IndexRoutes.getRoute(SeasonRoutes.SHOW_ADD)%>" method="post">
        <input type="hidden" name="id" value="${id}"/>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="title" placeholder="Title" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input type="number" name="number" placeholder="Show number" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="path" placeholder="Url" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-4">
                <input type="submit" value="Add" class="btn btn-success"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
