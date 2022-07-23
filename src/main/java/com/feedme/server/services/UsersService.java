package com.feedme.server.services;

import com.feedme.server.exceptions.NameAlreadyTakenException;
import com.feedme.server.model.PatchUserRequest;
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
    public User createUser(String email, String password) {
        Optional<User> previousUserWithThatName = usersRepository.findByEmail(email);
        if (previousUserWithThatName.isPresent()) {
            LOGGER.error("Email {} is already taken.", email);
            throw new NameAlreadyTakenException("Email " + email + " is already taken.");
        }
        User user = new User(email, password);
        User persistedUser = usersRepository.save(user);
        LOGGER.debug("User id: {}, email: {} created.", persistedUser.getId(), persistedUser.getEmail());
        return user;
    }

    @Transactional
    public User updateUser(User user, PatchUserRequest patchUserRequest) {
        if (patchUserRequest.getEmail().isPresent()) {
            user.setEmail(patchUserRequest.getEmail().get());
        }
        if (patchUserRequest.getPassword().isPresent()) {
            user.setEmail(patchUserRequest.getPassword().get());
        }
        User persistedUser = usersRepository.save(user);
        LOGGER.debug("User id: {}, name: {} updated.", persistedUser.getId(), persistedUser.getEmail());
        return user;
    }

    public Optional<User> getUser(long userId) {
        return usersRepository.findById(userId);
    }
}
