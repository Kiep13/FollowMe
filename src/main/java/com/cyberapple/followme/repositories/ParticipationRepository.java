package com.cyberapple.followme.repositories;

import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
    Iterable<Participation> findByUser(User user);
    Iterable<Participation> findByExcursionId(UUID excursionId);
}
