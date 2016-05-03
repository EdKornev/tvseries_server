package com.ek.serialsserver.tvseries.controllers;

import com.ek.serialsserver.tvseries.routes.TVSeriesRoutes;
import com.ek.serialsserver.tvseries.services.TVSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Eduard on 04.05.2016.
 */
@Controller
public class TVSeriesController {

    @Autowired private TVSeriesService service;

    @RequestMapping(value = TVSeriesRoutes.ALL, method = RequestMethod.GET)
    public String all(Model model) {
        model.addAttribute("tv_series", service.all());

        return "tvseries/all";
    }

    @RequestMapping(value = TVSeriesRoutes.ADD, method = RequestMethod.POST)
    public String add(@RequestParam String title, @RequestParam String description) {
        service.create(title, description);

        return "redirect: /tv/all";
    }
}
