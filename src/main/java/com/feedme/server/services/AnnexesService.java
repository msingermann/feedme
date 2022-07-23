package com.feedme.server.services;

import com.feedme.server.exceptions.AnnexNotFoundException;
import com.feedme.server.model.Annex;
import com.feedme.server.model.Feeder;
import com.feedme.server.repositories.AnnexesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AnnexesService {

    private final AnnexesRepository annexesRepository;

    @Autowired
    public AnnexesService(AnnexesRepository annexesRepository) {
        this.annexesRepository = annexesRepository;
    }

    public Annex registerAnnex(int port, String type, Feeder feeder) {
        Annex annex = new Annex(UUID.randomUUID(), port, type, feeder);
        feeder.getAnnexes().add(annex);
        annexesRepository.save(annex);
        return annex;
    }

    public Annex getAnnex(UUID id) {
        return annexesRepository.findById(id).orElseThrow(() -> new AnnexNotFoundException(id));
    }
}
