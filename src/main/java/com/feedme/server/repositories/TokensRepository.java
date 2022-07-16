package com.feedme.server.repositories;

import com.feedme.server.model.User;
import com.feedme.server.model.UserToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokensRepository extends CrudRepository<UserToken, UUID> {


}
