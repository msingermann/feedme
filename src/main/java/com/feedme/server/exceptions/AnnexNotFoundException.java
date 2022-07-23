package com.feedme.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AnnexNotFoundException extends RuntimeException {

    public AnnexNotFoundException(UUID id) {
        super(String.format("Annex with id %s does not exists.", id));
    }

}
