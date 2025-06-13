package com.cyberapple.followme.records;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public record ParticipantInput(
    @NotBlank(message = "Participant first name is required")
    String firstName, 
    
    @NotBlank(message = "Participant last name is required")
    String lastName, 
    
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth, 
    
    @NotBlank(message = "Participant citizenship is required")
    String citizenship, 
    
    @NotBlank(message = "Passport number is required")
    @Pattern(regexp = "^[A-Za-z]{2}\\d{7}$", message = "Invalid passport number format. Example: AB1234567") // ✅
    String passportNumber
) { 
}
