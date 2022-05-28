package com.feedme.server.controllers;

import com.feedme.server.model.CreateUserRequest;
import com.feedme.server.model.CreateUserResponse;
import com.feedme.server.model.User;
import com.feedme.server.services.UsersService;
import com.feedme.server.transformers.UsersTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    private static UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Create a user in the system.
     *
     * @param createUserRequest User creation request.
     * @return {@link CreateUserResponse}.
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        LOGGER.debug("Create Users request received.");
        User user = usersService.createUser(createUserRequest.getUsername(), createUserRequest.getPassword());
        return ResponseEntity.ok().body(UsersTransformer.transform(user));
    }
}
