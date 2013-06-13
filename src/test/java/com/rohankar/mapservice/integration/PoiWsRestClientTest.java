package com.rohankar.mapservice.integration;

import java.math.BigDecimal;
import java.net.URI;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rohankar.mapservice.bean.Poi;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Sagar Rohankar
 */
public class PoiWsRestClientTest {

    @Test
    public void should_create_poi() {
        // given
        final Poi poi = new Poi();
        poi.setName("Pizza Center");
        poi.setLatitude(new BigDecimal("23.23212"));
        poi.setLongitude(new BigDecimal("34.231312"));

        // when
        final URI location = new RestTemplate().postForLocation("http://localhost:8080/maps-service/pois", poi);

        // then
        assertThat(location.getPath(), endsWith("/pois/102"));
    }

    @Test
    public void should_read_poi() {
        // when
        final Poi poi = new RestTemplate().getForObject("http://localhost:8080/maps-service/pois/100", Poi.class);

        // then
        assertThat(poi.getId(), is("100"));
    }

    @Test
    public void should_delete_poi() {
        // given
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "12345");
        final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        // when
        final ResponseEntity<String> response =
            new RestTemplate().exchange("http://localhost:8080/maps-service/pois/101", HttpMethod.DELETE, httpEntity,
                String.class);

        // then
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
}
