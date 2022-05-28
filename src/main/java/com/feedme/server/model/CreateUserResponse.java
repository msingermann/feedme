package com.feedme.server.model;

public class CreateUserResponse {

    private final long id;

    public CreateUserResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}