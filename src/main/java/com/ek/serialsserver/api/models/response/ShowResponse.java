package com.ek.serialsserver.api.models.response;

import com.ek.serialsserver.season.models.ShowModel;

/**
 * Created by Eduard on 17.05.2016.
 */
public class ShowResponse {

    private Integer number;
    private String title;
    private String path;

    public ShowResponse(ShowModel model) {
        this.number = model.getNumber();
        this.title = model.getTitle();
        this.path = model.getPath();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
