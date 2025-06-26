package com.cyberapple.followme.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.cyberapple.followme.entities.Participant;

@Getter
@Setter
@AllArgsConstructor
public class ExcursionParticipantsDto {
    ExcursionDto excursion;
    List<Participant> participants;
}
