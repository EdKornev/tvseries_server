package com.ek.serialsserver.season.routes;

/**
 * Created by Eduard on 12.05.2016.
 */
public class SeasonRoutes {

    private static final String BASE_ROUTE = "/season";
    private static final String SHOW_BASE_ROUTE = BASE_ROUTE + "/show";

    public static final String ADD = BASE_ROUTE + "/add";
    public static final String REMOVE = BASE_ROUTE + "/remove";

    public static final String SHOW_ADD = SHOW_BASE_ROUTE + "/add";
    public static final String SHOW_REMOVE = SHOW_BASE_ROUTE + "/remove";
}
