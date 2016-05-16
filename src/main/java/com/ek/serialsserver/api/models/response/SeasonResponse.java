package com.ek.serialsserver.api.models.response;

import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.models.ShowModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 17.05.2016.
 */
public class SeasonResponse {

    private Integer number;
    private List<ShowResponse> shows = new ArrayList<ShowResponse>();

    public SeasonResponse(SeasonModel model) {
        this.number = model.getNumber();

        for (ShowModel show : model.getShows()) {
            this.shows.add(new ShowResponse(show));
        }
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<ShowResponse> getShows() {
        return shows;
    }

    public void setShows(List<ShowResponse> shows) {
        this.shows = shows;
    }
}
