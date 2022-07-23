package com.feedme.server.repositories;

import com.feedme.server.model.Pet;
import com.feedme.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PetsRepository extends CrudRepository<Pet, UUID> {
    Optional<Pet> findByIdAndUser(UUID id, User user);
}
