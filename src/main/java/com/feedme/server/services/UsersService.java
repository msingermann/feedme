package com.feedme.server.services;

import com.feedme.server.exceptions.EmailNotValidException;
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
import java.util.regex.Pattern;

@Component
public class UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);
    private final UsersRepository usersRepository;
    private final Pattern pattern;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(regexPattern);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public User createUser(String email,
                           String password,
                           String name,
                           String lastName,
                           String phone) {
        validateEmail(email);
        Optional<User> previousUserWithThatName = usersRepository.findByEmail(email);
        if (previousUserWithThatName.isPresent()) {
            LOGGER.error("Email {} is already taken.", email);
            throw new NameAlreadyTakenException("Email " + email + " is already taken.");
        }
        User user = new User(email, password, name, lastName, phone);
        User persistedUser = usersRepository.save(user);
        LOGGER.debug("User id: {}, email: {} created.", persistedUser.getId(), persistedUser.getEmail());
        return user;
    }

    private void validateEmail(String email) {
        if (!pattern.matcher(email).matches()) {
            throw new EmailNotValidException(email);
        }
    }

    @Transactional
    public User updateUser(User user, PatchUserRequest patchUserRequest) {
        if (patchUserRequest.getEmail().isPresent()) {
            user.setEmail(patchUserRequest.getEmail().get());
        }
        if (patchUserRequest.getPassword().isPresent()) {
            user.setPassword(patchUserRequest.getPassword().get());
        }
        if (patchUserRequest.getName().isPresent()) {
            user.setName(patchUserRequest.getName().get());
        }
        if (patchUserRequest.getLastName().isPresent()) {
            user.setLastName(patchUserRequest.getLastName().get());
        }
        if (patchUserRequest.getPhone().isPresent()) {
            user.setPhone(patchUserRequest.getPhone().get());
        }

        User persistedUser = usersRepository.save(user);
        LOGGER.info("User id: {}, name: {} updated.", persistedUser.getId(), persistedUser.getEmail());
        return user;
    }

    public Optional<User> getUser(long userId) {
        return usersRepository.findById(userId);
    }
}
