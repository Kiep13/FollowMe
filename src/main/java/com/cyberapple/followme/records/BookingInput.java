package com.cyberapple.followme.records;

import jakarta.validation.Valid;

public record BookingInput(@Valid Iterable<ParticipantInput> participants) {
}
