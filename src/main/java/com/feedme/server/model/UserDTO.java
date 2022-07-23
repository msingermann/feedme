package com.feedme.server.model;

import java.util.List;

public class UserDTO {

    private long id;

    private String username;

    public UserDTO(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

}
