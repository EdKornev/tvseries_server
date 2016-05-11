package com.ek.serialsserver.season.controllers;

import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.routes.SeasonRoutes;
import com.ek.serialsserver.season.services.SeasonService;
import com.ek.serialsserver.tvseries.routes.TVSeriesRoutes;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Eduard on 12.05.2016.
 */
@Controller
public class SeasonController {

    @Autowired private SeasonService seasonService;

    @RequestMapping(value = SeasonRoutes.ADD, method = RequestMethod.POST)
    public String add(@RequestParam ObjectId tvShowId, @RequestParam Integer number) {
        seasonService.create(number, tvShowId);

        return "redirect:/tv/" + tvShowId;
    }

    @RequestMapping(value = SeasonRoutes.SHOW_ADD, method = RequestMethod.GET)
    public String addShow(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);

        return "season/show/add";
    }

    @RequestMapping(value = SeasonRoutes.SHOW_ADD, method = RequestMethod.POST)
    public String addShow(@RequestParam ObjectId id, @RequestParam String title,
                          @RequestParam String path, @RequestParam Integer number) {
        SeasonModel seasonModel = seasonService.addShow(id, title, path, number);

        if (seasonModel == null) {
            return "redirect:/tv/all";
        } else {
            return "redirect:/tv/" + seasonModel.getTvSeries().getId();
        }
    }
}
