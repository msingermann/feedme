package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "feeders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feeder {

    /**
     * Feeder internal Id.
     */
    @Id
    private UUID id;

    /**
     * Feeder mac address.
     */
    @Column(name = "mac", nullable = false)
    private String mac;

    /**
     * Feeder Name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * User Id.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "feeder", fetch = FetchType.EAGER)
    private List<Annex> annexes;

    public Feeder(UUID id, String mac, String name, User user, List<Annex> annexes) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.user = user;
        this.annexes = annexes;
    }

    public Feeder() {
    }

    public UUID getId() {
        return id;
    }

    public String getMac() {
        return mac;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public List<Annex> getAnnexes() {
        return annexes;
    }

    public void setAnnexes(List<Annex> annexes) {
        this.annexes = annexes;
    }
}
