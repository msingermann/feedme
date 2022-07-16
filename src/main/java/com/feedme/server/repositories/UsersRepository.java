package com.feedme.server.repositories;

import com.feedme.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    /**
     * Finds users by name and password to check if password is correct.
     *
     * @param username User name.
     * @param password User password.
     * @return Optional of User. Empty if not present or password is not matched.
     */
    Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * Finds users by Name to check if the name is taken.
     *
     * @param username User name.
     * @return Returns a user if the name is taken.
     */
    Optional<User> findByUsername(String username);

}
