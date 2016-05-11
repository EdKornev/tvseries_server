package com.ek.serialsserver.season.models;

import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 12.05.2016.
 */
@Document
public class SeasonModel {

    @Id
    private ObjectId id;

    private Integer number;
    private List<ShowModel> shows = new ArrayList<ShowModel>();

    @DBRef
    private TVSeriesModel tvSeries;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<ShowModel> getShows() {
        return shows;
    }

    public void setShows(List<ShowModel> shows) {
        this.shows = shows;
    }

    public TVSeriesModel getTvSeries() {
        return tvSeries;
    }

    public void setTvSeries(TVSeriesModel tvSeries) {
        this.tvSeries = tvSeries;
    }
}
