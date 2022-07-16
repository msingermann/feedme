package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFeederRequest {

    private final String mac;
    private final String name;

    @JsonCreator
    public CreateFeederRequest(@JsonProperty(value = "mac", required = true) String mac,
                               @JsonProperty(value = "name", required = true) String name) {
        this.mac = mac;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }
}
