<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ek.serialsserver.season.routes.SeasonRoutes" %>
<%@ page import="com.ek.serialsserver.index.routes.IndexRoutes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%=IndexRoutes.getRoute("/static/css/bootstrap.css")%>" rel="stylesheet"/>
    <title>${tvSeriesModel.title}</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>${tvSeriesModel.title}</h1>
        </div>
    </div>
    <form action="<%=IndexRoutes.getRoute("")%>/tv/${tvSeriesModel.id}" method="post" enctype="multipart/form-data">
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
            <div class="form-group col-md-6">
                <input type="file" name="picture" class="form-control"/>
            </div>
            <c:if test="${not empty tvSeriesModel.picture}">
                <div class="col-md-4">
                    <img src="/pic/${tvSeriesModel.picture}" alt="${tvSeriesModel.originalTitle}" style="width: 100px;"/>
                </div>
            </c:if>
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

    <form action="<%=IndexRoutes.getRoute(SeasonRoutes.ADD)%>" method="post">
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
                <a href="${url_add_show}" class="btn btn-success" style="float: left; margin-right: 15px;">Add show</a>
                <form action="<%=IndexRoutes.getRoute(SeasonRoutes.REMOVE)%>" method="post">
                    <input type="hidden" name="id" value="${season.id}"/>
                    <button class="btn btn-default">
                        <span class="glyphicon glyphicon-remove"></span> Remove
                    </button>
                </form>
            </div>
        </div>
        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th style="width: 5%;">№</th>
                    <th style="width: 40%;">Title</th>
                    <th style="width: 40%;">Url</th>
                    <th style="width: 15%;"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${season.shows}" var="show">
                    <tr>
                        <td style="width: 5%;">${show.number}</td>
                        <td style="width: 40%;">${show.title}</td>
                        <td style="width: 40%;">${show.path}</td>
                        <td style="width: 15%;">
                            <form action="<%=IndexRoutes.getRoute(SeasonRoutes.SHOW_REMOVE)%>" method="post">
                                <input type="hidden" name="id" value="${season.id}"/>
                                <input type="hidden" name="number" value="${show.number}"/>
                                <button class="btn btn-default btn-sm">
                                    <span class="glyphicon glyphicon-remove"></span> Remove
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:forEach>
</div>
</body>
</html>
