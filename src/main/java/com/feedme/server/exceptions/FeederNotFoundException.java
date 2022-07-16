package com.feedme.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FeederNotFoundException extends RuntimeException {

    public FeederNotFoundException(UUID feederId) {
        super(String.format("Feeder with id %s does not exists.", feederId));
    }

}
