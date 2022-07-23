package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreatePetResponse {

    public final UUID id;

    public CreatePetResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
