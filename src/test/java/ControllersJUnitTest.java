import com.ek.serialsserver.configuration.AppConfiguration;
import com.ek.serialsserver.tvseries.routes.TVSeriesRoutes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Eduard on 31.05.2016.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(
//        classes = {AppConfiguration.class},
//        loader = AnnotationConfigWebContextLoader.class
//)
public class ControllersJUnitTest {

    @Autowired private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void createMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

//    @Test
//    public void createTvShow() throws Exception {
//        String title = "Test tv show";
//        String description = "Description of tv show";
//        String original = "Original title";
//        String producer = "Steve Martin";
//        String countries = "Russia, Germany";
//        String genres = "Comedy, Love story";
//
//        MockMultipartFile multipartFile = new MockMultipartFile("picture", new byte[0]);
//        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
//        contentTypeParams.put("boundary", "265001916915724");
//        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
//
//        this.mockMvc.perform(post(TVSeriesRoutes.ADD)
//                .contentType(mediaType)
//                .content(multipartFile.getBytes())
//                .param("title", title)
//                .param("description", description)
//                .param("original", original)
//                .param("producer", producer)
//                .param("countries", countries)
//                .param("genres", genres))
//                .andExpect(status().isOk());
//    }
}
