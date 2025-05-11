package com.cyberapple.followme.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
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

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String title;

    private String imageUrl;

    private String description;

    private LocalDate date;

    private Integer price;

    private Integer amountOfPlaces;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL)
    private List<Participation> participations;

    @Override
    public String toString() {
        return "Excursion{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
