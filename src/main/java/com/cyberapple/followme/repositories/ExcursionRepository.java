package com.cyberapple.followme.repositories;

import com.cyberapple.followme.entities.Excursion;
import org.springframework.data.repository.CrudRepository;

public interface ExcursionRepository extends CrudRepository<Excursion, String> {
    // This interface will automatically provide CRUD operations for the Excursion entity
    // No additional methods are needed unless you want to define custom queries
}
