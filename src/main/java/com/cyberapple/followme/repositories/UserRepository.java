package com.cyberapple.followme.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import com.cyberapple.followme.entities.User;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
