<%@ page import="com.ek.serialsserver.tvseries.routes.TVSeriesRoutes" %>
<%@ page import="com.ek.serialsserver.index.routes.IndexRoutes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Hello world</h1>
    <a href="<%=IndexRoutes.getRoute(TVSeriesRoutes.ALL)%>">TV Series</a>
</body>
</html>
