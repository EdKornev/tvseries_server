package com.ek.serialsserver.tvseries.services;

import com.ek.serialsserver.picture.models.PictureDoc;
import com.ek.serialsserver.picture.services.PictureService;
import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.models.ShowModel;
import com.ek.serialsserver.season.services.SeasonService;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.*;
import java.util.*;
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

        String frameHash = getFrameHash(fsId);
        String folders = getFolders(url, frameHash, fsId, "0");
        TagNode htmlFolders = cleaner.clean(folders);
        Object[] seasons = htmlFolders.evaluateXPath("//li/div/a/@name");

        int season = 1;

        for (Object seasonFolder : seasons) {
            folders = getFolders(url, frameHash, fsId, seasonFolder.toString().substring(2));
            TagNode htmlLanguages = cleaner.clean(folders);
            String language = htmlLanguages.evaluateXPath("//li/div/a/@name")[0].toString();

            folders = getFolders(url, frameHash, fsId, language.substring(2));
            TagNode htmlTranslate = cleaner.clean(folders);
            String folder = htmlTranslate.evaluateXPath("//li/div/a/@name")[0].toString();
            List<ShowModel> files = getFilesByFolder(fsId, folder);

            SeasonModel seasonModel = new SeasonModel();
            seasonModel.setNumber(season);
            seasonModel.setShows(files);
            seasonModel.setTvSeries(model);

            this.mongoTemplate.save(seasonModel);

            break;
        }

        return model;
    }

    private String getFrameHash(String id) throws IOException {
        String userAgent = "Mozilla/5.0";
        StringBuilder url = new StringBuilder("http://fs.to/jsitem/i")
                .append(id)
                .append("/status.js?hr=http://fs.to/video/cartoonserials/i")
                .append(id)
                .append("-simpsony.html&rf=");

        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", userAgent);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Pattern pattern = Pattern.compile("\\{(.*?)}");
        Matcher matcher = pattern.matcher(response.toString());

        String frameHash = "";
        if (matcher.find()) {
            String json = matcher.group(0);
            frameHash = json.split("'")[13];
        }

        return frameHash;
    }

    private String getFolders(String domain, String hash, String id, String folder) throws IOException {
        String userAgent = "Mozilla/5.0";
        Random random = new Random();
        double r = 0.01000000000001d + (0.90000000000001d - 0.01000000000001d) * random.nextDouble();
        StringBuilder url = new StringBuilder(domain)
                .append("?ajax")
                .append("&r=")
                .append(r)
                .append("&id=")
                .append(id)
                .append("&download=1")
                .append("&view=1")
                .append("&view_embed=1")
                .append("&blocked=0")
                .append("&frame_hash=")
                .append(hash)
                .append("&folder_quality=null")
                .append("&folder_lang=null")
                .append("&folder_translate=null")
                .append("&folder=")
                .append(folder)
                .append("&_=")
                .append(new Date().getTime());

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("195.138.78.143", 3128));
        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", userAgent);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private List<ShowModel> getFilesByFolder(String id, String folder) throws IOException {
        String userAgent = "Mozilla/5.0";
        StringBuilder url = new StringBuilder("http://fs.to/flist/i")
                .append(id)
                .append("?folder=")
                .append(folder.substring(2));

        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", userAgent);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        List<ShowModel> shows = new ArrayList<ShowModel>();
        List<String> paths = Arrays.asList(response.toString().split("http:"));

        for (int i=1; i<paths.size(); i++) {
            String lastName = paths.get(i).split("/")[paths.get(i).split("/").length - 1];
            String path = paths.get(i).split("/")[paths.get(i).split("/").length - 2];
            String fileName = lastName.split("_")[lastName.split("_").length - 1];
            String[] pathName = fileName.split("\\.");
            StringBuilder name = new StringBuilder();

            for (int j=0; j<pathName.length-1; j++) {
                name.append(pathName[j]).append(" ");
            }

            ShowModel showModel = new ShowModel();
            showModel.setNumber(i);
            showModel.setTitle(name.toString());
            try {
                showModel.setPath(this.getRealPath(path));
            } catch (Exception e) {
                continue;
            }

            shows.add(showModel);
        }

        return shows;
    }

    private String getRealPath(String path) throws IOException {
        String userAgent = "Mozilla/5.0";
        StringBuilder url = new StringBuilder("http://fs.to/get/playvideo/")
                .append(path)
                .append(".mp4?json_link");

        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", userAgent);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.toString());

        return json.get("link").asText();
    }
}
