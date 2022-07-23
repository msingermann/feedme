package com.feedme.server.model;

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
