package com.cyberapple.followme.records;

import java.time.LocalDate;

public record ExcursionInput(
    String title,
    String imageUrl,
    String description,
    LocalDate date,
    Integer price,
    Integer amountOfPlaces,
    String country
) {
}
