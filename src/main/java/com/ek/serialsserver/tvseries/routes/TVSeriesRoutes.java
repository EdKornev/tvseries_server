package com.ek.serialsserver.tvseries.routes;

/**
 * Created by Eduard on 04.05.2016.
 */
public class TVSeriesRoutes {
    private static final String BASE_ROUTE = "/tv";

    public static final String ALL = BASE_ROUTE + "/all";
    public static final String ADD = BASE_ROUTE + "/add";
    public static final String EDIT = BASE_ROUTE + "/{id}";
    public static final String REMOVE = BASE_ROUTE + "/remove";
    public static final String PARSE = BASE_ROUTE + "/parse";
}
