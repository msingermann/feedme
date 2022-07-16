package com.feedme.server.transformers;

import com.feedme.server.model.LoginUserResponse;
import com.feedme.server.model.UserToken;

public class UserTokensTransformer {

    public static LoginUserResponse transform(UserToken token) {
        return new LoginUserResponse(token.getToken());
    }

}
