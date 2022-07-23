package com.feedme.server.model;

public class UserDTO {

    private long id;

    private String email;

    public UserDTO(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDTO() {
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}
