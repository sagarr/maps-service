package com.rohankar.mapservice.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sagar Rohankar
 */
@Repository
public class RestClientAuthserverGateway implements AuthserverGateway {

    @Override
    public ResponseEntity<String> callAuthorizeService(final String authToken) {
        final RestTemplate restClient = new RestTemplate();
        final ResponseEntity<String> response =
            restClient.getForEntity("http://localhost:8080/authserver/authorize?access_token=" + authToken, String.class);
        return response;
    }
}
