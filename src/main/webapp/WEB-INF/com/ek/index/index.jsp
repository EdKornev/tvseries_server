<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ek.serialsserver.tvseries.routes.TVSeriesRoutes" %>
<%@ page import="com.ek.serialsserver.index.routes.IndexRoutes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Hello world</h1>
    <c:url value="<%=TVSeriesRoutes.ALL%>" var="urlAll"/>
    <a href="${urlAll}">TV Series</a>
</body>
</html>
