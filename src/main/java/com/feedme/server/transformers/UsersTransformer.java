package com.feedme.server.transformers;

import com.feedme.server.model.CreateUserResponse;
import com.feedme.server.model.User;

public class UsersTransformer {

    /**
     * Transforms a {@link User} into a {@link CreateUserResponse}.
     *
     * @param user User to transform to CreateUserResponse.
     * @return {@link CreateUserResponse}.
     */
    public static CreateUserResponse transform(User user) {
        return new CreateUserResponse(user.getId());
    }

}
