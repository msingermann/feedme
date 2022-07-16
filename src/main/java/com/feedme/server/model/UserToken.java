package com.feedme.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User entity.
 */
@Entity
@Table(name = "tokens")
public class UserToken {

    @Id
    private UUID token;

    @Column(name = "timestamp", insertable = false)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserToken(UUID token, User user) {
        this.token = token;
        this.user = user;
    }

    public UserToken() {
    }

    public UUID getToken() {
        return token;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }
}
