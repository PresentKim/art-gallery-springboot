package com.team4.artgallery.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotImplementsException extends ResponseStatusException {

    public NotImplementsException(String reason) {
        super(HttpStatus.NOT_IMPLEMENTED, reason);
    }

}
