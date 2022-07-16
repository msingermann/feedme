package com.feedme.server.repositories;

import com.feedme.server.model.Feeder;
import com.feedme.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface FeedersRepository extends CrudRepository<Feeder, UUID> {
    Optional<Feeder> findByIdAndUser(UUID id, User user);
}
