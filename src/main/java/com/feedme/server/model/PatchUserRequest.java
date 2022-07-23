package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class PatchUserRequest {

    private final Optional<String> email;
    private final Optional<String> password;

    @JsonCreator
    public PatchUserRequest(@JsonProperty(value = "email") Optional<String> email,
                            @JsonProperty(value = "password") Optional<String> password) {
        this.email = email;
        this.password = password;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getPassword() {
        return password;
    }
}
