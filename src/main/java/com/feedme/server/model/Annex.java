package com.feedme.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "annexes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Annex {

    @Id
    private UUID id;

    @Column(name = "port", nullable = false)
    private int port;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "deposit")
    private int depositLevel;

    @Column(name = "bowl")
    private int bowlContent;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "feeder_id", nullable = false)
    private Feeder feeder;

    @JsonManagedReference
    @OneToOne(mappedBy = "annex")
    private Pet pet;
    public Annex(UUID id, int port, String type, Feeder feeder) {
        this.id = id;
        this.port = port;
        this.type = type;
        this.feeder = feeder;
    }

    public Annex() {
    }

    public UUID getId() {
        return id;
    }

    public int getPort() {
        return port;
    }


    public String getType() {
        return type;
    }


    public int getDepositLevel() {
        return depositLevel;
    }

    public void setDepositLevel(int depositLevel) {
        this.depositLevel = depositLevel;
    }

    public int getBowlContent() {
        return bowlContent;
    }

    public void setBowlContent(int bowlContent) {
        this.bowlContent = bowlContent;
    }

    public Feeder getFeeder() {
        return feeder;
    }

}
