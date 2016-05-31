import com.ek.serialsserver.configuration.AppConfiguration;
import com.ek.serialsserver.season.models.SeasonModel;
import com.ek.serialsserver.season.routes.SeasonRoutes;
import com.ek.serialsserver.season.services.SeasonService;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import com.ek.serialsserver.tvseries.services.TVSeriesService;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Eduard on 01.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {AppConfiguration.class},
        loader = AnnotationConfigWebContextLoader.class
)
public class SeasonControllerTest {

    @Autowired private WebApplicationContext webApplicationContext;
    @Autowired private TVSeriesService tvSeriesService;
    @Autowired private SeasonService seasonService;

    private MockMvc mockMvc;
    private String tvShowId;
    private String seasonId;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        TVSeriesModel tvSeriesModel = this.tvSeriesService.create("", "", "", "", "", "", null);
        SeasonModel seasonModel = this.seasonService.create(1, tvSeriesModel.getId());

        this.tvShowId = tvSeriesModel.getId().toString();
        this.seasonId = seasonModel.getId().toString();
    }

    @Test
    public void testMethods() throws Exception {
        String title = "Show title";
        String path = "http://yandex.ru";

        // test add season
        this.mockMvc.perform(post(SeasonRoutes.ADD)
                .param("tvShowId", this.tvShowId)
                .param("number", "2"))
                .andExpect(status().is3xxRedirection());

        // test add show to season
        this.mockMvc.perform(get(SeasonRoutes.SHOW_ADD)
                .param("id", this.seasonId))
                .andExpect(status().isOk());

        this.mockMvc.perform(post(SeasonRoutes.SHOW_ADD)
                .param("id", seasonId)
                .param("title", title)
                .param("path", path)
                .param("number", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MvcResult checkErrorResult = this.mockMvc.perform(post(SeasonRoutes.SHOW_ADD)
                .param("id", new ObjectId().toString())
                .param("title", title)
                .param("path", path)
                .param("number", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Assert.assertEquals(checkErrorResult.getResponse().getRedirectedUrl(), "/tv/all");

        this.mockMvc.perform(post(SeasonRoutes.SHOW_REMOVE)
                .param("id", seasonId)
                .param("number", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        checkErrorResult = this.mockMvc.perform(post(SeasonRoutes.SHOW_REMOVE)
                .param("id", new ObjectId().toString())
                .param("number", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Assert.assertEquals(checkErrorResult.getResponse().getRedirectedUrl(), "/tv/all");
    }

    @After
    public void remove() {
        this.tvSeriesService.remove(new ObjectId(this.tvShowId));
    }
}
