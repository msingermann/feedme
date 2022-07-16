package com.feedme.server.services;

import com.feedme.server.model.Feeder;
import com.feedme.server.model.User;
import com.feedme.server.repositories.FeedersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FeedersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedersService.class);

    private final FeedersRepository feedersRepository;

    @Autowired
    public FeedersService(FeedersRepository feedersRepository) {
        this.feedersRepository = feedersRepository;
    }


    public Feeder registerFeeder(User user, String mac, String name) {
        Feeder newFeeder = new Feeder(UUID.randomUUID(), mac, name, user);
        feedersRepository.save(newFeeder);
        return newFeeder;
    }

    public Optional<Feeder> getFeeder(UUID id, User user) {
        return feedersRepository.findByIdAndUser(id, user);
    }


}
