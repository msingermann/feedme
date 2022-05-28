package com.feedme.server.services;

import com.feedme.server.exceptions.NameAlreadyTakenException;
import com.feedme.server.model.User;
import com.feedme.server.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public User createUser(String username, String password) {
        Optional<User> previousUserWithThatName = usersRepository.findByUsername(username);
        if (previousUserWithThatName.isPresent()) {
            LOGGER.error("Name {} is already taken.", username);
            throw new NameAlreadyTakenException("Name " + username + " is already taken.");
        }
        User user = new User(username, password);
        User persistedUser = usersRepository.save(user);
        LOGGER.debug("User id: {}, name: {} created.", persistedUser.getId(), persistedUser.getUsername());
        return user;
    }

}
