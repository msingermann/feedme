package com.feedme.server.controllers;

import com.feedme.server.exceptions.PetNotFoundException;
import com.feedme.server.filters.TokenValidatorFilter;
import com.feedme.server.model.*;
import com.feedme.server.services.AnnexesService;
import com.feedme.server.services.PetsService;
import com.feedme.server.transformers.PetsTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class PetsController {

    private final PetsService petsService;
    private final AnnexesService annexesService;

    @Autowired
    public PetsController(PetsService petsService,
                          AnnexesService annexesService) {
        this.petsService = petsService;
        this.annexesService = annexesService;
    }


    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public CreatePetResponse createPet(HttpServletRequest httpServletRequest,
                                       @RequestBody CreatePetRequest createPetRequest) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        Annex annex = annexesService.getAnnex(createPetRequest.getAnnexId());
        Pet newPet = petsService.createPet(user, createPetRequest.getName(), createPetRequest.getTag(), annex);
        return PetsTransformer.transform(newPet);

    }

    @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET)
    public Pet getPet(HttpServletRequest httpServletRequest,
                      @PathVariable UUID petId) {
        User user = (User) httpServletRequest.getAttribute(TokenValidatorFilter.USER);
        return petsService.getPet(petId, user).orElseThrow(() -> new PetNotFoundException(petId));

    }
}
