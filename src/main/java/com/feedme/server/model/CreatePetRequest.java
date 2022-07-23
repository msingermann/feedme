package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePetRequest {

    private final String name;
    private final String tag;

    @JsonCreator
    public CreatePetRequest(@JsonProperty(value = "name", required = true) String name,
                            @JsonProperty(value = "tag", required = true) String tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }
}
