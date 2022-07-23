package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pets")
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "annex_id")
    private Annex annex;

    public Pet(UUID id, String name, String tag, User user, Annex annex) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.user = user;
        this.annex = annex;
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
