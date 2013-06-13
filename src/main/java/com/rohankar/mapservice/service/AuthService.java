package com.rohankar.mapservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohankar.mapservice.exception.AuthorizationException;
import com.rohankar.mapservice.gateway.AuthserverGateway;

/**
 * @author Sagar Rohankar
 */
@Service
public class AuthService {

    @Autowired
    private AuthserverGateway asGateway;

    public void doAuthorization(final String authToken) {
        final ResponseEntity<String> response = asGateway.callAuthorizeService(authToken);
        if (response == null || response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new AuthorizationException("Invalid auth token: " + authToken);
        }
    }

}
