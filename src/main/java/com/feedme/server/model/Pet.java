package com.feedme.server.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pets")
public class Pet {

    /**
     * Pet Id.
     */
    @Id
    private UUID id;

    /**
     * User Name (must be unique).
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Tag Id.
     */
    @Column(name = "tag", nullable = false)
    private String tag;

    /**
     * User Id.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Pet(UUID id, String name, String tag, User user) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.user = user;
    }
    public Pet() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public User getUser() {
        return user;
    }
}
