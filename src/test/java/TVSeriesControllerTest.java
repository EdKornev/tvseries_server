import com.ek.serialsserver.configuration.AppConfiguration;
import com.ek.serialsserver.tvseries.models.TVSeriesModel;
import com.ek.serialsserver.tvseries.routes.TVSeriesRoutes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Eduard on 31.05.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {AppConfiguration.class},
        loader = AnnotationConfigWebContextLoader.class
)
public class TVSeriesControllerTest {

    @Autowired private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void createMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getCreateTvShow() throws Exception {
        this.mockMvc.perform(get(TVSeriesRoutes.ADD))
                .andExpect(status().isOk());
    }

    @Test
    public void testMethods() throws Exception {
        String title = "Test tv show";
        String description = "Description of tv show";
        String original = "Original title";
        String producer = "Steve Martin";
        String countries = "Russia, Germany";
        String genres = "Comedy, Love story";

        // test add tv show
        MockMultipartFile multipartFile = new MockMultipartFile("picture", new byte[0]);

        MvcResult addResult = this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(TVSeriesRoutes.ADD)
                .file(multipartFile)
                .param("title", title)
                .param("description", description)
                .param("originalTitle", original)
                .param("producer", producer)
                .param("countries", countries)
                .param("genres", genres))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // test (post) edit tv show
        String[] partsOfRedirectUrl = addResult.getResponse().getRedirectedUrl().split("/");
        String tvShowId = partsOfRedirectUrl[partsOfRedirectUrl.length-1];

        String updateTitle = "Test tv show updated";

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(TVSeriesRoutes.EDIT, tvShowId)
                .param("title", updateTitle)
                .param("description", description)
                .param("originalTitle", original)
                .param("producer", producer)
                .param("countries", countries)
                .param("genres", genres))
                .andExpect(status().is3xxRedirection());

        // check updated tv show
        MvcResult editResult = this.mockMvc.perform(get(TVSeriesRoutes.EDIT, tvShowId))
                .andExpect(status().isOk())
                .andReturn();

        TVSeriesModel model = (TVSeriesModel) editResult.getModelAndView().getModel().get("tvSeriesModel");
        assertEquals(model.getId().toString(), tvShowId);
        assertEquals(model.getTitle(), updateTitle);

        // test all tv shows
        MvcResult allResult = this.mockMvc.perform(get(TVSeriesRoutes.ALL))
                .andExpect(status().isOk())
                .andReturn();

        List<TVSeriesModel> checkList = (List<TVSeriesModel>) allResult.getModelAndView().getModel().get("tv_series");
        assertTrue(checkList.size() > 0);

        // test remove tv show
        this.mockMvc.perform(post(TVSeriesRoutes.REMOVE)
                .param("id", tvShowId))
                .andExpect(status().is3xxRedirection());
    }
}
