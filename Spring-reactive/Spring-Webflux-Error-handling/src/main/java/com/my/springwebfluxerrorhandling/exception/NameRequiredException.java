package com.my.springwebfluxerrorhandling.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NameRequiredException extends ResponseStatusException {

    public NameRequiredException(HttpStatusCode status, String message, Throwable e) {
        super(status, message, e);
    }
}
