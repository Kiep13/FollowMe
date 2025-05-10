package com.cyberapple.followme.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    // TODO: should it be enum?
    private String citizenship;

    // TODO: add validation for this field
    private String passportNumber;

    @ManyToOne
    @JoinColumn(
            name = "excursion_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Excursion excursion;

//    @ManyToOne
//    @JoinColumn(
//            name = "user_id",
//            referencedColumnName = "id",
//            nullable = false
//    )
//    private User user;

    @Override
    public String toString() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", citizenship='" + citizenship + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", excursion=" + excursion +
                '}';
    }
}
