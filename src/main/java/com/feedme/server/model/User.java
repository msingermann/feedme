package com.feedme.server.model;

import javax.persistence.*;

/**
 * User entity.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * User Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * User Name (must be unique).
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * User Password Hashed.
     */
    @Column(name = "password", nullable = false)
    private String password;

    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
