package com.feedme.server.filters;

import com.feedme.server.exceptions.ForbbidenException;
import com.feedme.server.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token Validation Filter.
 */
@Component
public class TokenValidatorFilter implements HandlerInterceptor {

    public static final String BEARER_PREFIX = "bearer ";
    public static final String USER_ID = "userId";
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidatorFilter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        //TODO: Here we should use a token not user id.
        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaderValue == null ||authHeaderValue.isEmpty()) {
            throw new ForbbidenException();
        }
        String token = authHeaderValue.substring(BEARER_PREFIX.length());
        LOGGER.info("User validated, ID: {}", token);
        request.setAttribute(USER_ID, token);
        return true;
    }
}