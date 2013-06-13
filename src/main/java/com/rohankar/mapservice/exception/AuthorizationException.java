package com.rohankar.mapservice.exception;

/**
 * @author Sagar Rohankar
 */
@SuppressWarnings("serial")
public class AuthorizationException extends RuntimeException {

    public AuthorizationException(final String msg) {
        super(msg);
    }

}
