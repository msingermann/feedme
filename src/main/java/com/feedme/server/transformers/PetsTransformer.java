package com.feedme.server.transformers;

import com.feedme.server.model.CreatePetResponse;
import com.feedme.server.model.Pet;

public class PetsTransformer {

    public static CreatePetResponse transform(Pet pet) {
        return new CreatePetResponse(pet.getId());
    }


}
