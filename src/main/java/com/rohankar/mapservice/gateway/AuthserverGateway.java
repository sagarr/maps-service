package com.rohankar.mapservice.gateway;

import org.springframework.http.ResponseEntity;

/**
 * @author Sagar Rohankar
 */
public interface AuthserverGateway {

    ResponseEntity<String> callAuthorizeService(String authToken);

}
