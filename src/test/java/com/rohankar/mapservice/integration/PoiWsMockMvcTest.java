package com.rohankar.mapservice.integration;

import java.math.BigDecimal;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.rohankar.mapservice.bean.Poi;
import com.rohankar.mapservice.filter.AuthFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * https://github.com/SpringSource/spring-test-mvc/tree/master/src/test/java/org/springframework/test/web/server/samples
 * 
 * @author Sagar Rohankar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml", "/applicationContext-controllers.xml",
    "/applicationContext-test.xml"})
public class PoiWsMockMvcTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private AuthFilter filter;

    @Test
    public void should_create_poi() throws Exception {
        // given
        final Poi poi = new Poi();
        poi.setName("Pizza Center");
        poi.setLatitude(new BigDecimal("23.23212"));
        poi.setLongitude(new BigDecimal("34.231312"));

        final String poiJson = new ObjectMapper().writeValueAsString(poi);

        // when and then
        MockMvcBuilders.webAppContextSetup(wac).build()
            .perform(post("/pois").content(poiJson).contentType(MediaType.APPLICATION_JSON)) //
            .andDo(print()) //
            .andExpect(status().isCreated()) //
            .andExpect(redirectedUrl("http://localhost/pois/102"));
    }

    @Test
    public void should_read_poi() throws Exception {
        MockMvcBuilders.webAppContextSetup(wac).build() //
            .perform(get("/pois/{id}", "100")).andDo(print()) //
            .andExpect(status().isOk()) //
            .andExpect(content().contentType("application/json;charset=UTF-8")) //
            .andExpect(jsonPath("$.id").value("100")) //
            .andExpect(jsonPath("$.name").value("Pizza Center"));
    }

    @Test
    public void should_delete_poi() throws Exception {
        MockMvcBuilders.webAppContextSetup(wac).addFilter(filter, "/pois/*").build() //
            .perform(delete("/pois/{id}", "101").header("Authorization", "12345")) //
            .andDo(print()) //
            .andExpect(status().isNoContent());
    }
}
