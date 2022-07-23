package com.feedme.server.controllers;

import com.feedme.server.filters.TokenValidatorFilter;
import com.feedme.server.model.*;
import com.feedme.server.services.AuthService;
import com.feedme.server.services.UsersService;
import com.feedme.server.transformers.UserTokensTransformer;
import com.feedme.server.transformers.UsersTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;
    private final AuthService authService;

    @Autowired
    public UsersController(UsersService usersService, AuthService authService) {
        this.usersService = usersService;
        this.authService = authService;
    }

    /**
     * Create a user in the system.
     *
     * @param createUserRequest User creation request.
     * @return {@link CreateUserResponse}.
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        LOGGER.debug("Create Users request received.");
        User user = usersService.createUser(createUserRequest.getEmail(), createUserRequest.getPassword(), createUserRequest.getName(), createUserRequest.getLastName(), createUserRequest.getPhone());
        return UsersTransformer.transform(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PATCH)
    public UserDTO patchUser(HttpServletRequest httpServletRequest,
                             @RequestBody PatchUserRequest patchUserRequest) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        User updatedUser = usersService.updateUser(user, patchUserRequest);
        return UsersTransformer.transformToDTO(updatedUser);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public UserDTO getUser(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        return UsersTransformer.transformToDTO(user);

    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginUserResponse createUser(@RequestBody LoginUserRequest loginUserRequest) {
        LOGGER.debug("Login Users request received.");
        UserToken token = authService.login(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        return UserTokensTransformer.transform(token);
    }
}
