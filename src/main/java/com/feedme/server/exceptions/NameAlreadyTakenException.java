package com.feedme.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NameAlreadyTakenException extends RuntimeException {
    public NameAlreadyTakenException(String message) {
        super(message);
    }
}
