package com.ek.serialsserver.api.models.response;

import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 13.05.2016.
 */
public class TVShowResponse {

    private String id;
    private String title;
    private String originalTitle;
    private String description;
    private String producer;
    private String picture;
    private List<String> genres = new ArrayList<String>();
    private List<String> countries = new ArrayList<String>();

    private List<SeasonResponse> seasons = new ArrayList<SeasonResponse>();

    public TVShowResponse() {
    }

    public TVShowResponse(TVSeriesModel model, List<SeasonResponse> seasons) {
        this.id = model.getId().toHexString();
        this.title = model.getTitle();
        this.originalTitle = model.getOriginalTitle();
        this.description = model.getDescription();
        this.producer = model.getProducer();
        this.genres = model.getGenres();
        this.countries = model.getCountries();
        this.seasons = seasons;

        if (model.getPicture() != null) {
            this.picture = model.getPicture().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<SeasonResponse> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonResponse> seasons) {
        this.seasons = seasons;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
