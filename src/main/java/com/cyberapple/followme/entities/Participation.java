package com.cyberapple.followme.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Excursion excursion;

    @OneToMany(mappedBy = "participation", cascade = CascadeType.ALL)
    private List<Participant> participants;

//    @ManyToOne
//    @JoinColumn(
//            name = "user_id",
//            referencedColumnName = "id",
//            nullable = false
//    )
//    private User user;
}
