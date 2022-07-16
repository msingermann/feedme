package com.feedme.server.services;

import com.feedme.server.exceptions.ForbbidenException;
import com.feedme.server.exceptions.UnauthorizedException;
import com.feedme.server.model.User;
import com.feedme.server.model.UserToken;
import com.feedme.server.repositories.TokensRepository;
import com.feedme.server.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthService {

    private final UsersRepository usersRepository;
    private final TokensRepository tokensRepository;

    @Autowired
    public AuthService(UsersRepository usersRepository, TokensRepository tokensRepository) {
        this.usersRepository = usersRepository;
        this.tokensRepository = tokensRepository;
    }

    public UserToken login(String username, String password) {
        User user = usersRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new UnauthorizedException());
        return tokensRepository.save(new UserToken(UUID.randomUUID(), user));
    }

    public User getUserFromToken(UUID token) {
        UserToken userToken = tokensRepository.findById(token).orElseThrow(() -> new ForbbidenException());
        return userToken.getUser();
    }

    //TODO: make tokens expire.
}
