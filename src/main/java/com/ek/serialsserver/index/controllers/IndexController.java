package com.ek.serialsserver.index.controllers;

import com.ek.serialsserver.index.routes.IndexRoutes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Eduard on 04.05.2016.
 */
@Controller
public class IndexController {

    @RequestMapping(value = IndexRoutes.BASE_ROUTE)
    public String index() {
        return "index/index";
    }
}
