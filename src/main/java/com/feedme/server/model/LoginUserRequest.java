package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserRequest {

    private final String email;
    private final String password;

    @JsonCreator
    public LoginUserRequest(@JsonProperty(value = "email", required = true) String email,
                            @JsonProperty(value = "password", required = true) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
