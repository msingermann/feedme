package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

    private final String email;
    private final String password;
    private final String name;
    private final String lastName;
    private final String phone;

    @JsonCreator
    public CreateUserRequest(@JsonProperty(value = "email", required = true) String email,
                             @JsonProperty(value = "password", required = true) String password,
                             @JsonProperty(value = "name") String name,
                             @JsonProperty(value = "lastName") String lastName,
                             @JsonProperty(value = "phone") String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }

    public CreateUserRequest(String email,
                             String password) {
        this.email = email;
        this.password = password;
        this.name = null;
        this.lastName = null;
        this.phone = null;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
