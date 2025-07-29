package com.cyberapple.followme.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.cyberapple.followme.records.ExcursionInput;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Excursion {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    private String title;

    private String imageUrl;

    private String description;

    private LocalDate date;

    private Integer price;

    private Integer amountOfPlaces;

    @Enumerated(EnumType.STRING)
    private Country country;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Participation> participations;

    public Excursion(ExcursionInput excursionInput) {
        this.title = excursionInput.title();
        this.imageUrl = excursionInput.imageUrl();
        this.description = excursionInput.description();
        this.date = excursionInput.date();
        this.price = excursionInput.price();
        this.amountOfPlaces = excursionInput.amountOfPlaces();
        this.country = excursionInput.country();
    }

    @Override
    public String toString() {
        return "Excursion{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
