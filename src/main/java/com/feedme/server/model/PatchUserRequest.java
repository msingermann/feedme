package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class PatchUserRequest {

    private final Optional<String> email;
    private final Optional<String> password;
    private final Optional<String> name;
    private final Optional<String> lastName;
    private final Optional<String> phone;

    @JsonCreator
    public PatchUserRequest(@JsonProperty(value = "email") String email,
                            @JsonProperty(value = "password") String password,
                            @JsonProperty(value = "name") String name,
                            @JsonProperty(value = "lastName") String lastName,
                            @JsonProperty(value = "phone") String phone) {

        this.email = Optional.ofNullable(email);
        this.password = Optional.ofNullable(password);
        this.name = Optional.ofNullable(name);
        this.lastName = Optional.ofNullable(lastName);
        this.phone = Optional.ofNullable(phone);
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public Optional<String> getPhone() {
        return phone;
    }
}
