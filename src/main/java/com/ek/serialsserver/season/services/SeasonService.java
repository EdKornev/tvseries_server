package com.ek.serialsserver.season.services;

import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.models.ShowModel;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eduard on 12.05.2016.
 */
@Repository
public class SeasonService {

    @Autowired private MongoTemplate mongoTemplate;

    public List<SeasonModel> findByTvShowId(ObjectId id) {
        Criteria criteria = new Criteria("tvSeries.id").is(id);
        Sort sort = new Sort(Sort.Direction.ASC, "number");

        Query query = new Query(criteria);
        query.with(sort);

        return mongoTemplate.find(query, SeasonModel.class);
    }

    public void create(Integer number, ObjectId tvShowId) {
        TVSeriesModel tvSeriesModel = mongoTemplate.findById(tvShowId, TVSeriesModel.class);

        if (tvSeriesModel == null) {
            return;
        }

        SeasonModel seasonModel = new SeasonModel();
        seasonModel.setNumber(number);
        seasonModel.setTvSeries(tvSeriesModel);

        mongoTemplate.save(seasonModel);
    }

    public SeasonModel addShow(ObjectId id, String title, String path, Integer number) {
        SeasonModel seasonModel = findById(id);

        if (seasonModel == null) {
            return null;
        }

        ShowModel showModel = new ShowModel();
        showModel.setTitle(title);
        showModel.setPath(path);
        showModel.setNumber(number);

        seasonModel.getShows().add(showModel);

        mongoTemplate.save(seasonModel);

        return seasonModel;
    }

    public SeasonModel removeShow(ObjectId id, Integer number) {
        SeasonModel seasonModel = findById(id);

        if (seasonModel == null) {
            return null;
        }

        for (ShowModel showModel : seasonModel.getShows()) {
            if (showModel.getNumber().equals(number)) {
                seasonModel.getShows().remove(showModel);
                mongoTemplate.save(seasonModel);
                break;
            }
        }

        return seasonModel;
    }

    public SeasonModel remove(ObjectId id) {
        SeasonModel seasonModel = findById(id);

        if (seasonModel == null) {
            return null;
        }

        mongoTemplate.remove(seasonModel);

        return seasonModel;
    }

    public SeasonModel findById(ObjectId id) {
        return mongoTemplate.findById(id, SeasonModel.class);
    }
}
