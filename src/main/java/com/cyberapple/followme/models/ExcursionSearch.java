package com.cyberapple.followme.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionSearch {
    private int minPrice;
    private int maxPrice;
    private String[] countries;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean hasAvailableSeats;
}
