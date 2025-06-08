package com.cyberapple.followme.dtos;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.entities.Participant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class ParticipationDto {
    String id;

    Excursion excursion;

    Iterable<Participant> participants;
}