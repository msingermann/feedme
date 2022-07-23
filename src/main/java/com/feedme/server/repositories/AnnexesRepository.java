package com.feedme.server.repositories;

import com.feedme.server.model.Annex;
import com.feedme.server.model.Feeder;
import com.feedme.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnnexesRepository extends CrudRepository<Annex, UUID> {
}
