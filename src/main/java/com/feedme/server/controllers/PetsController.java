package com.feedme.server.controllers;

import com.feedme.server.exceptions.PetNotFoundException;
import com.feedme.server.filters.TokenValidatorFilter;
import com.feedme.server.model.CreatePetRequest;
import com.feedme.server.model.CreatePetResponse;
import com.feedme.server.model.Pet;
import com.feedme.server.model.User;
import com.feedme.server.services.PetsService;
import com.feedme.server.transformers.PetsTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class PetsController {

    private final PetsService petsService;

    @Autowired
    public PetsController(PetsService petsService) {
        this.petsService = petsService;
    }


    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public CreatePetResponse createPet(HttpServletRequest httpServletRequest,
                                       @RequestBody CreatePetRequest createPetRequest) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        Pet newPet = petsService.createPet(user, createPetRequest.getName(), createPetRequest.getTag());
        return PetsTransformer.transform(newPet);

    }

    @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET)
    public Pet getPet(HttpServletRequest httpServletRequest,
                              @PathVariable UUID petId) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        return petsService.getPet(petId, user).orElseThrow(() -> new PetNotFoundException(petId));

    }
}
