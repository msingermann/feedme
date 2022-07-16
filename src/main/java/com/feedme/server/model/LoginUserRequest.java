package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserRequest {

    private final String username;
    private final String password;

    @JsonCreator
    public LoginUserRequest(@JsonProperty(value = "username", required = true) String username,
                            @JsonProperty(value = "password", required = true) String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
