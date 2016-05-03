package com.ek.serialsserver.tvseries.services;

import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eduard on 04.05.2016.
 */
@Repository
public class TVSeriesService {

    @Autowired private MongoTemplate mongoTemplate;

    public List<TVSeriesModel> all() {
        return mongoTemplate.findAll(TVSeriesModel.class);
    }

    public void create(String title, String description) {
        TVSeriesModel model = new TVSeriesModel();
        model.setTitle(title);
        model.setDescription(description);

        mongoTemplate.save(model);
    }
}
