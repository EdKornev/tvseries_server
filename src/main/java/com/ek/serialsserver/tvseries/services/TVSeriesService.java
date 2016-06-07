package com.ek.serialsserver.tvseries.services;

import com.ek.serialsserver.picture.models.PictureDoc;
import com.ek.serialsserver.picture.services.PictureService;
import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.services.SeasonService;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import org.bson.types.ObjectId;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eduard on 04.05.2016.
 */
@Repository
public class TVSeriesService {

    @Autowired private MongoTemplate mongoTemplate;
    @Autowired private SeasonService seasonService;
    @Autowired private PictureService pictureService;

    public List<TVSeriesModel> all() {
        return this.mongoTemplate.findAll(TVSeriesModel.class);
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
    public TVSeriesModel create(String title, String description, String original, String producer, String countries, String genres, MultipartFile picture) {
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

        model.setPicture(this.pictureService.save(picture));

        this.mongoTemplate.save(model);

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
    public void update(ObjectId id, String title, String description,
                       String original, String producer, String countries,
                       String genres, MultipartFile picture) {
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

        model.setPicture(this.pictureService.save(picture));

        this.mongoTemplate.save(model);
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

        this.mongoTemplate.remove(tvSeriesModel);

        List<SeasonModel> seasons = this.seasonService.findByTvShowId(id);
        for (SeasonModel seasonModel : seasons) {
            this.mongoTemplate.remove(seasonModel);
        }
    }

    /**
     * Find tv show by id
     * @param id - id show
     * @return
     */
    public TVSeriesModel findById(ObjectId id) {
        return this.mongoTemplate.findById(id, TVSeriesModel.class);
    }

    /**
     * Parse url from fs
     * http://fs.to/video/cartoonserials/iw8zj3q6u31SdzspwoEkWA-simpsony.html
     * @param url
     * @return
     */
    public TVSeriesModel parse(String url) throws IOException, XPatherException {
        // form fs id
        String fsId = "";
        Pattern pattern = Pattern.compile("/i(.*?)-");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String mentionText = matcher.group(0);
            fsId = mentionText.replaceAll("/i|-", "");
        }

        // parse html
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode html = cleaner.clean(new URL(url));

        String title = html.evaluateXPath("//div[@class='b-tab-item__title-inner']/span/text()")[0].toString().trim();
        String originalTitle = html.evaluateXPath("//div[@class='b-tab-item__title-origin']/text()")[0].toString().trim();
        String description = html.evaluateXPath("//p[@class='item-decription full']/text()")[0].toString().trim();
        String producer = html.evaluateXPath("//span[@itemprop='director']/a/span[@itemprop='name']/text()")[0].toString();
        String imageUrl = "http:";
        imageUrl = imageUrl.concat(html.evaluateXPath("//a[@class='images-show']/img/@src")[0].toString().trim());
        Object[] genres = html.evaluateXPath("//span[@itemprop='genre']/text()");
        Object[] countries = html.evaluateXPath("//span[@class='tag-country-flag']/../text()");

        // form model
        TVSeriesModel model = new TVSeriesModel();
        model.setFsId(fsId);
        model.setTitle(title);
        model.setOriginalTitle(originalTitle);
        model.setDescription(description);
        model.setProducer(producer);

        for (Object genre : genres) {
            model.getGenres().add(genre.toString());
        }

        for (Object country : countries) {
            model.getCountries().add(country.toString().replaceFirst("&(.*?);", ""));
        }

        // save picture
        URL pictureUrl = new URL(imageUrl);
        InputStream in = new BufferedInputStream(pictureUrl.openStream());

        ObjectId picId = this.pictureService.save(in);
        model.setPicture(picId);

        this.mongoTemplate.save(model);

        return model;
    }
}
