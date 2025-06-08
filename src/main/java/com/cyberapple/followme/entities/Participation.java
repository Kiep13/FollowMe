package com.cyberapple.followme.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(
            name = "excursion_id",
            referencedColumnName = "id",
            nullable = false
    )
    @JsonBackReference
    private Excursion excursion;

    @OneToMany(mappedBy = "participation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Participant> participants;

   @ManyToOne
   @JoinColumn(
           name = "user_id",
           referencedColumnName = "id",
           nullable = false
   )
   private User user;

   private LocalDate createdAt;
}
