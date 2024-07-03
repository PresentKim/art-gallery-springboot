package com.team4.artgallery.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SqlException extends ResponseStatusException {

    public SqlException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

}
