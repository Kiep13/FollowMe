package com.cyberapple.followme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.cyberapple.followme.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
