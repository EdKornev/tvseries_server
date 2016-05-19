package com.ek.serialsserver.tvseries.controllers;

import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import com.ek.serialsserver.tvseries.routes.TVSeriesRoutes;
import com.ek.serialsserver.season.services.SeasonService;
import com.ek.serialsserver.tvseries.services.TVSeriesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Eduard on 04.05.2016.
 */
@Controller
public class TVSeriesController {

    @Autowired private TVSeriesService tvSeriesService;
    @Autowired private SeasonService seasonService;

    @RequestMapping(value = TVSeriesRoutes.ALL, method = RequestMethod.GET)
    public String all(Model model) {
        model.addAttribute("tv_series", tvSeriesService.all());

        return "tvseries/all";
    }

    @RequestMapping(value = TVSeriesRoutes.ADD, method = RequestMethod.GET)
    public String add() {
        return "tvseries/add";
    }

    @RequestMapping(value = TVSeriesRoutes.ADD, method = RequestMethod.POST)
    public String add(@RequestParam String title, @RequestParam String description,
                      @RequestParam String originalTitle, @RequestParam String producer,
                      @RequestParam String countries, @RequestParam String genres,
                      @RequestParam MultipartFile picture) {
        TVSeriesModel model = tvSeriesService.create(title, description, originalTitle, producer, countries, genres, picture);

        return "redirect:/tv/" + model.getId();
    }

    @RequestMapping(value = TVSeriesRoutes.EDIT, method = RequestMethod.GET)
    public String edit(@PathVariable ObjectId id, Model model) {
        model.addAttribute("tvSeriesModel", tvSeriesService.findById(id));
        model.addAttribute("seasons", seasonService.findByTvShowId(id));

        return "tvseries/edit";
    }

    @RequestMapping(value = TVSeriesRoutes.EDIT, method = RequestMethod.POST)
    public String edit(@PathVariable ObjectId id,
                       @RequestParam String title, @RequestParam String description,
                       @RequestParam String originalTitle, @RequestParam String producer,
                       @RequestParam String countries, @RequestParam String genres,
                       @RequestParam(value = "picture", required = false) MultipartFile picture) {
        tvSeriesService.update(id, title, description, originalTitle, producer, countries, genres, picture);

        return "redirect:/tv/" + id;
    }

    @RequestMapping(value = TVSeriesRoutes.REMOVE, method = RequestMethod.POST)
    public String remove(@RequestParam ObjectId id) {
        tvSeriesService.remove(id);

        return "redirect:/tv/all";
    }
}
