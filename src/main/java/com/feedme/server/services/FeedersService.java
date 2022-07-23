package com.feedme.server.services;

import com.feedme.server.model.Annex;
import com.feedme.server.model.Feeder;
import com.feedme.server.model.User;
import com.feedme.server.repositories.FeedersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedersService.class);

    private final FeedersRepository feedersRepository;
    private final AnnexesService annexesService;
    @Autowired
    public FeedersService(FeedersRepository feedersRepository,
                          AnnexesService annexesService) {
        this.feedersRepository = feedersRepository;
        this.annexesService = annexesService;
    }


    public Feeder registerFeeder(User user, String mac, String name) {
        Feeder newFeeder = new Feeder(UUID.randomUUID(), mac, name, user, new ArrayList<>());
        feedersRepository.save(newFeeder);
        annexesService.registerAnnex(0, "FOOD", newFeeder);
        return newFeeder;
        //TODO al crear feeder crear un anexo 0 automagicamente

    }

    public Optional<Feeder> getFeeder(UUID id, User user) {
        return feedersRepository.findByIdAndUser(id, user);
    }


}
