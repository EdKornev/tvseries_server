package com.ek.serialsserver.tvseries.services;

import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.services.SeasonService;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import org.bson.types.ObjectId;
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
    @Autowired private SeasonService seasonService;

    public List<TVSeriesModel> all() {
        return mongoTemplate.findAll(TVSeriesModel.class);
    }

    /**
     * Create new tv show
     * @param title
     * @param description
     * @param original
     * @param producer
     * @param countries
     * @param genres
     */
    public TVSeriesModel create(String title, String description, String original, String producer, String countries, String genres) {
        TVSeriesModel model = new TVSeriesModel();
        model.setTitle(title);
        model.setDescription(description);
        model.setOriginalTitle(original);
        model.setProducer(producer);

        for(String country : countries.split(", ")) {
            model.getCountries().add(country);
        }

        for(String genre : genres.split(", ")) {
            model.getGenres().add(genre);
        }

        mongoTemplate.save(model);

        return model;
    }

    /**
     * Update tv show
     * @param id
     * @param title
     * @param description
     * @param original
     * @param producer
     * @param countries
     * @param genres
     */
    public void update(ObjectId id, String title, String description, String original, String producer, String countries, String genres) {
        TVSeriesModel model = findById(id);

        if (model == null) {
            return;
        }

        model.setTitle(title);
        model.setDescription(description);
        model.setOriginalTitle(original);
        model.setProducer(producer);

        model.getCountries().clear();
        for(String country : countries.split(", ")) {
            model.getCountries().add(country);
        }

        model.getGenres().clear();
        for(String genre : genres.split(", ")) {
            model.getGenres().add(genre);
        }

        mongoTemplate.save(model);
    }

    /**
     * Remove tv show
     * @param id
     */
    public void remove(ObjectId id) {
        TVSeriesModel tvSeriesModel = findById(id);

        if (tvSeriesModel == null) {
            return;
        }

        mongoTemplate.remove(tvSeriesModel);

        List<SeasonModel> seasons = seasonService.findByTvShowId(id);
        for (SeasonModel seasonModel : seasons) {
            mongoTemplate.remove(seasonModel);
        }
    }

    /**
     * Find tv show by id
     * @param id - id show
     * @return
     */
    public TVSeriesModel findById(ObjectId id) {
        return mongoTemplate.findById(id, TVSeriesModel.class);
    }
}
