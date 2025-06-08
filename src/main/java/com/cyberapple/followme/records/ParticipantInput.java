package com.cyberapple.followme.records;

import java.time.LocalDate;

public record ParticipantInput(String firstName, String lastName, LocalDate dateOfBirth, String citizenship, String passportNumber) { 
}
