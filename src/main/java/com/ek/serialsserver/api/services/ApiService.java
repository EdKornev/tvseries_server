package com.ek.serialsserver.api.services;

import com.ek.serialsserver.api.models.response.BaseResponse;
import com.ek.serialsserver.api.models.response.TVShowResponse;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import com.ek.serialsserver.tvseries.services.TVSeriesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 13.05.2016.
 */
@Component
public class ApiService {

    @Autowired private TVSeriesService tvSeriesService;

    /**
     * Form list of all tv shows
     * @return
     */
    public BaseResponse<List<TVShowResponse>> getAllTVShows() {
        List<TVShowResponse> results = new ArrayList<TVShowResponse>();

        for (TVSeriesModel tvShow : tvSeriesService.all()) {
            results.add(new TVShowResponse(tvShow));
        }

        BaseResponse<List<TVShowResponse>> response = new BaseResponse<List<TVShowResponse>>();
        response.setResults(results);

        return response;
    }

    /**
     * From response for one tv show
     * @param id - tv show id
     * @return
     */
    public BaseResponse<TVShowResponse> getTVShow(ObjectId id) {
        BaseResponse<TVShowResponse> response = new BaseResponse<TVShowResponse>();
        response.setResults(new TVShowResponse(tvSeriesService.findById(id)));

        return response;
    }
}
