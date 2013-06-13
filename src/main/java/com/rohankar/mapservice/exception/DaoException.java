package com.rohankar.mapservice.exception;

/**
 * @author Sagar Rohankar
 */
@SuppressWarnings("serial")
public class DaoException extends RuntimeException {

    public DaoException(final String mesg) {
        super(mesg);
    }

}
