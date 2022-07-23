package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreatePetRequest {

    private final String name;
    private final String tag;
    private final UUID annexId;

    @JsonCreator
    public CreatePetRequest(@JsonProperty(value = "name", required = true) String name,
                            @JsonProperty(value = "tag", required = true) String tag,
                            @JsonProperty(value = "annex", required = true) UUID annexId) {
        this.name = name;
        this.tag = tag;
        this.annexId = annexId;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public UUID getAnnexId() {
        return annexId;
    }
}
