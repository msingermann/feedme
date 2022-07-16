package com.feedme.server.model;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * User Password Hashed.
     */
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Feeder> feeders;

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
