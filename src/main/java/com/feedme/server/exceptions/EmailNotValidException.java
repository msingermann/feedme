package com.feedme.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailNotValidException extends RuntimeException {

    public EmailNotValidException(String email) {
        super(String.format("Email %s is not valid.", email));
    }

}
