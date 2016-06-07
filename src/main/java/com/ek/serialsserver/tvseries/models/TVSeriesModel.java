package com.ek.serialsserver.tvseries.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 04.05.2016.
 */
@Document
public class TVSeriesModel {

    @Id
    private ObjectId id;

    private String fsId;
    private String title;
    private String originalTitle;
    private String description;
    private String producer;
    private ObjectId picture;
    private List<String> genres = new ArrayList<String>();
    private List<String> countries = new ArrayList<String>();

    public String formCountries() {
        return formValue(countries);
    }

    public String formGenres() {
        return formValue(genres);
    }

    private String formValue(List<String> results) {
        StringBuilder builder = new StringBuilder();

        boolean isFirst = true;
        for (String result : results) {
            if (isFirst) {
                builder.append(result);

                isFirst = false;
            } else {
                builder.append(", ").append(result);
            }
        }

        return builder.toString();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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

    public ObjectId getPicture() {
        return picture;
    }

    public void setPicture(ObjectId picture) {
        this.picture = picture;
    }

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }
}
