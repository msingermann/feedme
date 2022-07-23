package com.feedme.server.repositories;

import com.feedme.server.model.UserToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokensRepository extends CrudRepository<UserToken, UUID> {
    @Override
    @Query(value = "SELECT * FROM tokens WHERE token = (:token) AND timestamp >= now() - interval '1 hours' ", nativeQuery = true)
    Optional<UserToken> findById(UUID token);
}
