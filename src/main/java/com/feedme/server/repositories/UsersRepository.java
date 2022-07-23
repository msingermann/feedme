package com.feedme.server.repositories;

import com.feedme.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    /**
     * Finds users by name and password to check if password is correct.
     *
     * @param email User email.
     * @param password User password.
     * @return Optional of User. Empty if not present or password is not matched.
     */
    Optional<User> findByEmailAndPassword(String email, String password);

    /**
     * Finds users by Name.
     *
     * @param email User email.
     * @return Returns a user if the name is found.
     */
    Optional<User> findByEmail(String email);

}
