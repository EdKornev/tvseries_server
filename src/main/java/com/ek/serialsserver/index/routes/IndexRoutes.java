package com.ek.serialsserver.index.routes;

import com.ek.serialsserver.configuration.AppProperties;

/**
 * Created by Eduard on 04.05.2016.
 */
public class IndexRoutes {

    private static AppProperties appProperties;

    private static final String BASE_PREFIX = "/serials_server";

    public static final String BASE_ROUTE = "/";

    public static String getRoute(String url) {
        if (appProperties.getDebug()) {
            return url;
        } else {
            return BASE_PREFIX + url;
        }
    }

    public static void setAppProperties(AppProperties properties) {
        appProperties = properties;
    }
}
