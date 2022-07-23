package com.feedme.server.services;

import com.feedme.server.model.Pet;
import com.feedme.server.model.User;
import com.feedme.server.repositories.PetsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PetsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedersService.class);

    private final PetsRepository petsRepository;

    @Autowired
    public PetsService(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    public Pet createPet(User user, String name, String tag) {
        Pet newPet = new Pet(UUID.randomUUID(), name, tag, user);
        petsRepository.save(newPet);
        return newPet;
    }

    public Optional<Pet> getPet(UUID id, User user) {
        return petsRepository.findByIdAndUser(id, user);
    }

}
