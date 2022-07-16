package com.feedme.server.controllers;

import com.feedme.server.exceptions.FeederNotFoundException;
import com.feedme.server.exceptions.UserNotFoundException;
import com.feedme.server.filters.TokenValidatorFilter;
import com.feedme.server.model.CreateFeederRequest;
import com.feedme.server.model.CreateFeederResponse;
import com.feedme.server.model.Feeder;
import com.feedme.server.model.User;
import com.feedme.server.services.FeedersService;
import com.feedme.server.services.UsersService;
import com.feedme.server.transformers.FeedersTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class FeedersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedersController.class);
    private final FeedersService feedersService;
    private final UsersService usersService;

    @Autowired
    public FeedersController(FeedersService feedersService, UsersService usersService) {
        this.feedersService = feedersService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "/feeders", method = RequestMethod.POST)
    public CreateFeederResponse registerFeeder(HttpServletRequest httpServletRequest,
                                               @RequestBody CreateFeederRequest createFeederRequest) {
        long userId = Long.parseLong((String) httpServletRequest.getAttribute(TokenValidatorFilter.USER_ID));
        User user = usersService.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Feeder newFeeder = feedersService.registerFeeder(user, createFeederRequest.getMac(), createFeederRequest.getName());
        return FeedersTransformer.transform(newFeeder);

    }

    @RequestMapping(value = "/feeders/{feederId}", method = RequestMethod.GET)
    public Feeder registerFeeder(HttpServletRequest httpServletRequest,
                                               @PathVariable UUID feederId) {
        long userId = Long.parseLong((String) httpServletRequest.getAttribute(TokenValidatorFilter.USER_ID));
        User user = usersService.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return feedersService.getFeeder(feederId, user).orElseThrow(() -> new FeederNotFoundException(feederId));

    }

}
