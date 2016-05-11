<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ek.serialsserver.season.routes.SeasonRoutes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/static/css/bootstrap.css" rel="stylesheet"/>
    <title>${tvSeriesModel.title}</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>${tvSeriesModel.title}</h1>
        </div>
    </div>
    <form action="/tv/${tvSeriesModel.id}" method="post">
        <div class="row">
            <div class="form-group col-md-6">
                <input name="title" placeholder="Title" class="form-control" value="${tvSeriesModel.title}"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="originalTitle" placeholder="Original title" class="form-control" value="${tvSeriesModel.originalTitle}"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="description" placeholder="Description" class="form-control" value="${tvSeriesModel.description}"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="producer" placeholder="Producer" class="form-control" value="${tvSeriesModel.producer}"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="countries" placeholder="Countries" class="form-control" value="${tvSeriesModel.formCountries()}"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <input name="genres" placeholder="Genres" class="form-control" value="${tvSeriesModel.formGenres()}"/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-4">
                <input type="submit" value="Update" class="btn btn-success"/>
            </div>
        </div>
    </form>

    <div class="row">
        <div class="col-md-12">
            <h3>Add season</h3>
        </div>
    </div>

    <form action="<%=SeasonRoutes.ADD%>" method="post">
        <input type="hidden" name="tvShowId" value="${tvSeriesModel.id}"/>

        <div class="row">
            <div class="form-group col-md-6">
                <input type="number" name="number" placeholder="Season number" class="form-control"/>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-4">
                <input type="submit" value="Add season" class="btn btn-success"/>
            </div>
        </div>
    </form>

    <c:forEach items="${seasons}" var="season">
        <div class="row">
            <div class="col-md-9">
                <h3>Season ${season.number}</h3>
                <c:url value="<%=SeasonRoutes.SHOW_ADD%>" var="url_add_show">
                    <c:param name="id" value="${season.id}"/>
                </c:url>
                <a href="${url_add_show}" class="btn btn-success">Add show</a>
            </div>
        </div>
        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th>№</th>
                    <th>Title</th>
                    <th>Url</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${season.shows}" var="show">
                    <tr>
                        <td>${show.number}</td>
                        <td>${show.title}</td>
                        <td>${show.path}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:forEach>
</div>
</body>
</html>