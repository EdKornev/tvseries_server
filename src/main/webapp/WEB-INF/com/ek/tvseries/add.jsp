<%@ page import="com.ek.serialsserver.tvseries.routes.TVSeriesRoutes" %>
<%@ page import="com.ek.serialsserver.index.routes.IndexRoutes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%=IndexRoutes.getRoute("/static/css/bootstrap.css")%>" rel="stylesheet"/>
    <title>Add tv</title>
</head>
<body>
<div class="container">
    <form action="<%=IndexRoutes.getRoute(TVSeriesRoutes.ADD)%>" method="post" enctype="multipart/form-data">
        <div class="row">
            <div class="form-group col-md-6">
                <input name="title" placeholder="Title" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="originalTitle" placeholder="Original title" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="description" placeholder="Description" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="producer" placeholder="Producer" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="countries" placeholder="Countries" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="genres" placeholder="Genres" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input type="file" name="picture" class="form-control"/>
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
