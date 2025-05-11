package com.cyberapple.followme.repositories;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.entities.Excursion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExcursionRepository extends CrudRepository<Excursion, String> {

    @Query("""
        SELECT new com.cyberapple.followme.dtos.ExcursionDto(
            e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces, COUNT(pp.id)
        )
        FROM Excursion e
        LEFT JOIN e.participations p
        LEFT JOIN p.participants pp
        GROUP BY e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces
    """)
    List<ExcursionDto> findAllExcursionsWithAvailablePlaces();
}
