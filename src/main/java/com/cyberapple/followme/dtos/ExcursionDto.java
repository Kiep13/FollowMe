package com.cyberapple.followme.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExcursionDto {
    private String id;

    private String title;

    private String imageUrl;

    private String description;

    private LocalDate date;

    private Integer price;

    private Integer amountOfPlaces;

    private Integer availablePlaces;

    public ExcursionDto(String id, String title, String imageUrl, String description, LocalDate date, Integer price, Integer amountOfPlaces, Long amountOfParticipants) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.date = date;
        this.price = price;
        this.amountOfPlaces = amountOfPlaces;

        Integer amountOfParticipantsInt = amountOfParticipants != null ? amountOfParticipants.intValue() : 0;
        this.availablePlaces = amountOfPlaces - amountOfParticipantsInt;
    }
}
