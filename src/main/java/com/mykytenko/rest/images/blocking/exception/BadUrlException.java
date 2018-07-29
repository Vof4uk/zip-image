package com.mykytenko.rest.images.blocking.exception;

/**
 * This exception reflects cases when url provided by client is not valid
 * or does not point to expected/supported resources.
 */
public class BadUrlException extends RuntimeException {
    public BadUrlException(Exception e) {
        super(e);
    }

    public BadUrlException(String message) {
        super(message);
    }
}
