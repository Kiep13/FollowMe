package com.cyberapple.followme.entities;

import com.cyberapple.followme.records.ParticipantInput;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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

    @Enumerated(EnumType.STRING)
    private Country citizenship;

    private String passportNumber;

    @ManyToOne
    @JoinColumn(
            name = "participation_id",
            referencedColumnName = "id",
            nullable = false
    )
    @JsonBackReference
    private Participation participation;

    public Participant(ParticipantInput participantApiInput) {
        this.firstName = participantApiInput.firstName();
        this.lastName = participantApiInput.lastName();
        this.dateOfBirth = participantApiInput.dateOfBirth();
        this.citizenship = Country.valueOf(participantApiInput.citizenship());
        this.passportNumber = participantApiInput.passportNumber();
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
