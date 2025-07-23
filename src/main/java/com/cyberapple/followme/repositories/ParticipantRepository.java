package com.cyberapple.followme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cyberapple.followme.entities.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, String> {
}
