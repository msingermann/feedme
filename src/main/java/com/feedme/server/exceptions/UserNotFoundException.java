package com.feedme.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long userId) {
        super(String.format("User with id %d does not exists.", userId));
    }

}
