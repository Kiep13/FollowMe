package com.cyberapple.followme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.entities.Excursion;

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

    @Query("""
        SELECT new com.cyberapple.followme.dtos.ExcursionDto(
            e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces, COUNT(pp.id)
        )
        FROM Excursion e
        LEFT JOIN e.participations p
        LEFT JOIN p.participants pp
        WHERE e.id = :id
        GROUP BY e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces
    """)
    Optional<ExcursionDto> findExcursionWithAvailablePlacesById(String id);

    @Query("SELECT DISTINCT e.country FROM Excursion e")
    List<String> getCountryList();
}
