package com.cyberapple.followme.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cyberapple.followme.dtos.PriceRange;
import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.entities.Excursion;

public interface ExcursionRepository extends JpaRepository<Excursion, String> {

    Excursion findByTitle(String string);

    @Query("""
        SELECT new com.cyberapple.followme.dtos.ExcursionDto(
            e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces, COUNT(pp.id), e.country
        )
        FROM Excursion e
        LEFT JOIN e.participations p
        LEFT JOIN p.participants pp
        GROUP BY e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces
    """)
    List<ExcursionDto> findAllExcursionsWithAvailablePlaces();

    @Query("""
        SELECT new com.cyberapple.followme.dtos.ExcursionDto(
            e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces, COUNT(pp.id), e.country
        )
        FROM Excursion e
        LEFT JOIN e.participations p
        LEFT JOIN p.participants pp
        WHERE e.id = :id
        GROUP BY e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces
    """)
    Optional<ExcursionDto> findExcursionWithAvailablePlacesById(String id);

    @Query("""
        SELECT new com.cyberapple.followme.dtos.ExcursionDto(
            e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces, COUNT(pp.id), e.country
        )
        FROM Excursion e
        LEFT JOIN e.participations p
        LEFT JOIN p.participants pp
        WHERE (:minPrice IS NULL OR e.price >= :minPrice)
          AND (:maxPrice IS NULL OR e.price <= :maxPrice)
          AND (:countries IS NULL OR e.country IN :countries)
          AND (:startDate IS NULL OR e.date >= :startDate)
          AND (:endDate IS NULL OR e.date <= :endDate)
          AND (:isActive IS NULL OR :isActive IS false OR e.date >= CURRENT_DATE)
        GROUP BY e.id, e.title, e.imageUrl, e.description, e.date, e.price, e.amountOfPlaces
       HAVING (:hasAvailableSeats = false OR (e.amountOfPlaces - COUNT(pp.id)) > 0)
    """)
    List<ExcursionDto> searchExcursions(Integer minPrice, Integer maxPrice, List<String> countries, LocalDate startDate, LocalDate endDate, boolean hasAvailableSeats, boolean isActive, Pageable pageable);

    @Query("SELECT DISTINCT e.country FROM Excursion e")
    List<String> getCountryList();

    @Query("SELECT new com.cyberapple.followme.dtos.PriceRange(MIN(e.price), MAX(e.price)) FROM Excursion e")
    PriceRange getPriceRange();

    @Modifying
    @Transactional
    @Query("UPDATE Excursion e SET e.amountOfPlaces = :amountOfPlaces WHERE e.id = :id")
    void updateExcursionPlaces(String id, int amountOfPlaces);
}
