package com.cyberapple.followme.repositories;

import org.springframework.data.repository.CrudRepository;
import com.cyberapple.followme.entities.Participant;

public interface ParticipantRepository extends CrudRepository<Participant, String> {
}
