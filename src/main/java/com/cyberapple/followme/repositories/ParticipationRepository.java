package com.cyberapple.followme.repositories;

import com.cyberapple.followme.entities.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, String> {
}
