package com.feedme.server.transformers;

import com.feedme.server.model.CreateUserResponse;
import com.feedme.server.model.User;
import com.feedme.server.model.UserDTO;

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

    public static UserDTO transformToDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail());
    }

}
