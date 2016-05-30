import com.ek.serialsserver.configuration.AppConfiguration;
import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.services.SeasonService;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import com.ek.serialsserver.tvseries.services.TVSeriesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Eduard on 30.05.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {AppConfiguration.class},
        loader = AnnotationConfigWebContextLoader.class
)
public class MongoJUnitTest {

    @Autowired private TVSeriesService tvSeriesService;
    @Autowired private SeasonService seasonService;

    @Test
    public void testTVSeriesService() {
        assertEquals(
                "class com.ek.serialsserver.tvseries.services.TVSeriesService",
                this.tvSeriesService.getClass().toString()
        );
    }

    @Test
    public void testMongoMethods() {
        // test properties
        String title = "Test tv show";
        String updateTitle = "Test tv show 2";
        String description = "Description of tv show";
        String original = "Original title";
        String producer = "Steve Martin";
        String countries = "Russia, Germany";
        String updateCountries = "Russia";
        String genres = "Comedy, Love story";
        String updateGenres = "Comedy";
        MultipartFile picture = null;

        Integer seasonNumber = 1;

        String showTitle = "Title show";
        String showPath = "http://some.ru/long_path";
        Integer showNumber = 1;

        // test create tv series
        TVSeriesModel model = tvSeriesService.create(title, description, original, producer, countries, genres, picture);

        assertNotNull(model);
        assertNotNull(model.getId());

        // test get all tv series
        List<TVSeriesModel> list = tvSeriesService.all();
        assertTrue(list.size() > 0);

        // test update tv show
        tvSeriesService.update(model.getId(), updateTitle, description, producer, producer, updateCountries, updateGenres, picture);

        TVSeriesModel secondModel = tvSeriesService.findById(model.getId());
        assertEquals(model.getId(), secondModel.getId());
        assertEquals(secondModel.getTitle(), updateTitle);
        assertNotEquals(secondModel.getCountries().size(), model.getCountries().size());
        assertNotEquals(secondModel.getGenres().size(), model.getGenres().size());

        // test add season
        SeasonModel seasonModel = seasonService.create(seasonNumber, model.getId());
        assertNotNull(seasonModel);

        // test find by id season
        SeasonModel checkFindById = seasonService.findById(seasonModel.getId());
        assertNotNull(checkFindById);

        // test find all seasons by tv show id
        List<SeasonModel> listSeasons = seasonService.findByTvShowId(model.getId());
        assertTrue(listSeasons.size() > 0);

        // test add and remove shows from season
        seasonModel = seasonService.addShow(seasonModel.getId(), showTitle, showPath, showNumber);
        assertEquals(seasonModel.getShows().size(), 1);

        seasonModel = seasonService.removeShow(seasonModel.getId(), showNumber);
        assertEquals(seasonModel.getShows().size(), 0);

        // test remove season
        seasonService.remove(model.getId());

        SeasonModel checkRemove = seasonService.findById(model.getId());
        assertNull(checkRemove);

        // test remove and find by id tv show
        tvSeriesService.remove(model.getId());
        TVSeriesModel checkRemoveModel = tvSeriesService.findById(model.getId());
        assertNull(checkRemoveModel);
    }

    @Test
    public void testSeasonService() {
        assertEquals(
                "class com.ek.serialsserver.season.services.SeasonService",
                this.seasonService.getClass().toString()
        );
    }
}
