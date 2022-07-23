package com.feedme.server.model;

public class UserDTO {

    private long id;

    private String email;
    private String name;
    private String lastName;
    private String phone;

    public UserDTO(long id,
                   String email,
                   String name,
                   String lastName,
                   String phone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }

    public UserDTO() {
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
}
