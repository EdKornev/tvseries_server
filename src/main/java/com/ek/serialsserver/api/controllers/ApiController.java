package com.ek.serialsserver.api.controllers;

import com.ek.serialsserver.api.models.response.BaseResponse;
import com.ek.serialsserver.api.models.response.TVShowResponse;
import com.ek.serialsserver.api.routes.ApiRoutes;
import com.ek.serialsserver.api.services.ApiService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Eduard on 13.05.2016.
 */
@Controller
public class ApiController {

    @Autowired private ApiService service;

    @ResponseBody
    @RequestMapping(value = ApiRoutes.TV_SHOW_ALL, method = RequestMethod.GET, produces = "application/json")
    public BaseResponse<List<TVShowResponse>> getAllTV() {
        return service.getAllTVShows();
    }

    @ResponseBody
    @RequestMapping(value = ApiRoutes.TV_SHOW_ONE, method = RequestMethod.GET, produces = "application/json")
    public BaseResponse<TVShowResponse> getOneTV(@PathVariable ObjectId id) {
        return service.getTVShow(id);
    }
}
