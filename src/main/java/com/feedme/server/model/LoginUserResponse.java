package com.feedme.server.model;

import java.util.UUID;

public class LoginUserResponse {

    private final UUID token;

    public LoginUserResponse(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}