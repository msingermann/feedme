package com.feedme.server.model;

import java.util.UUID;

public class CreateFeederResponse {

    public final UUID id;

    public CreateFeederResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
