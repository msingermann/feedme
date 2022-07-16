package com.feedme.server.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "feeders")
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
     * Feeder internal Id.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Feeder(UUID id, String mac, String name, User user) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.user = user;
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
}
