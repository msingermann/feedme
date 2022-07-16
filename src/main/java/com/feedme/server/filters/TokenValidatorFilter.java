package com.feedme.server.filters;

import com.feedme.server.exceptions.ForbbidenException;
import com.feedme.server.model.User;
import com.feedme.server.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Token Validation Filter.
 */
@Component
public class TokenValidatorFilter implements HandlerInterceptor {

    public static final String BEARER_PREFIX = "bearer ";
    public static final String USER_ID = "userId";
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidatorFilter.class);

    private final AuthService authService;

    @Autowired
    public TokenValidatorFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        try {
            String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authHeaderValue.substring(BEARER_PREFIX.length());
            User user = authService.getUserFromToken(UUID.fromString(token));
            LOGGER.info("User validated! User: {}, ID: {}.", user.getUsername(), user.getId());
            request.setAttribute(USER_ID, user.getId());
            return true;
        } catch(Exception e) {
            throw new ForbbidenException();
        }
    }
}