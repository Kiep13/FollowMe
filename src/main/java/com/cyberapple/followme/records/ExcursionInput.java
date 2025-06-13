package com.cyberapple.followme.records;

import java.time.LocalDate;

import com.cyberapple.followme.entities.Country;

public record ExcursionInput(
    String title,
    String imageUrl,
    String description,
    LocalDate date,
    Integer price,
    Integer amountOfPlaces,
    Country country
) {
}
