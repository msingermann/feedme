package com.feedme.server.controllers;

import com.feedme.server.exceptions.FeederNotFoundException;
import com.feedme.server.filters.TokenValidatorFilter;
import com.feedme.server.model.CreateFeederRequest;
import com.feedme.server.model.CreateFeederResponse;
import com.feedme.server.model.Feeder;
import com.feedme.server.model.User;
import com.feedme.server.services.FeedersService;
import com.feedme.server.transformers.FeedersTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
public class FeedersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedersController.class);
    private final FeedersService feedersService;

    @Autowired
    public FeedersController(FeedersService feedersService) {
        this.feedersService = feedersService;
    }

    @RequestMapping(value = "/feeders", method = RequestMethod.POST)
    public CreateFeederResponse registerFeeder(HttpServletRequest httpServletRequest,
                                               @RequestBody CreateFeederRequest createFeederRequest) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        Feeder newFeeder = feedersService.registerFeeder(user, createFeederRequest.getMac(), createFeederRequest.getName());

        return FeedersTransformer.transform(newFeeder);
    }

    @RequestMapping(value = "/feeders/{feederId}", method = RequestMethod.GET)
    public Feeder getFeeder(HttpServletRequest httpServletRequest,
                            @PathVariable UUID feederId) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        return feedersService.getFeeder(feederId, user).orElseThrow(() -> new FeederNotFoundException(feederId));
    }

    @RequestMapping(value = "/feeders", method = RequestMethod.GET)
    public List<Feeder> getFeeder(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        return feedersService.getFeeders(user);
    }

}
