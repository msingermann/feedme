package com.feedme.server.transformers;

import com.feedme.server.model.CreateFeederResponse;
import com.feedme.server.model.Feeder;

public class FeedersTransformer {

    public static CreateFeederResponse transform(Feeder feeder) {
        return new CreateFeederResponse(feeder.getId());
    }

}
