package com.cyberapple.followme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cyberapple.followme.entities.Participant;

import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}
