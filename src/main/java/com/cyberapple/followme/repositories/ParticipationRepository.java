package com.cyberapple.followme.repositories;

import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, String> {
    Iterable<Participation> findByUser(User user);
    Iterable<Participation> findByExcursionId(String excursionId);
}
