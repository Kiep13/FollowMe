package com.cyberapple.followme.api.controllers;

import com.cyberapple.followme.models.ExcursionSearch;
import com.cyberapple.followme.records.UpdateExcursionPlaces;
import com.cyberapple.followme.services.ExcursionReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cyberapple.followme.dtos.PriceRange;
import com.cyberapple.followme.records.ExcursionInput;
import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.dtos.ExcursionParticipantsDto;
import com.cyberapple.followme.services.ExcursionService;

import io.micrometer.core.annotation.Counted;

import com.cyberapple.followme.exceptions.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("api/excursions")
@AllArgsConstructor
public class ApiExcursionController {

    private final ExcursionService excursionService;

    private final ExcursionReportService excursionReportService;

    @GetMapping("")
    @Counted(value = "api.calls.excursions", description = "Number of calls to /api/excursions")
    public Iterable<ExcursionDto> getAllExcursions() {
        return excursionService.getAllExcursions();
    }

    @GetMapping("/search")
    @Counted(value = "api.calls.excursions", description = "Number of calls to /api/excursions")
    public Iterable<ExcursionDto> searchExcursions(
            @RequestParam("minPrice") int minPrice,
            @RequestParam("maxPrice") int maxPrice,
            @RequestParam("countries") String[] countries,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam(value = "hasAvailableSeats", required = false, defaultValue = "false") boolean hasAvailableSeats,
            @RequestParam(value = "isActive", required = false, defaultValue = "false") boolean isActive,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        ExcursionSearch excursionSearch = ExcursionSearch.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .countries(countries)
                .startDate(startDate)
                .endDate(endDate)
                .hasAvailableSeats(hasAvailableSeats)
                .isActive(isActive)
                .build();

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("date").ascending());

        return excursionService.searchExcursions(excursionSearch, pageable);
    }

    @GetMapping("/price-range")
    public PriceRange getPriceRange() {
        return excursionService.getPriceRange();
    }

    @PostMapping("/add")
    @PostAuthorize("hasRole('ADMIN')")
    public void createExcursion(@RequestBody ExcursionInput excursionInput) {
        excursionService.createExcursion(excursionInput);
    }

    @GetMapping("/{id}")
    public ExcursionDto getExcursionById(@PathVariable String id) throws NotFoundException {
        return excursionService.getExcursionById(id);
    }

    @GetMapping("/{id}/participants")
    @PreAuthorize("hasRole('ADMIN')")
    public ExcursionParticipantsDto getExcursionParticipants(@PathVariable String id) throws NotFoundException {
        return excursionService.getExcursionParticipants(id);
    }

    @GetMapping("/{id}/participants/report")
    @PreAuthorize("hasRole('ADMIN')")
    public void getExcursionParticipants(@PathVariable String id, HttpServletResponse response) throws NotFoundException, IOException {
        excursionReportService.generateParticipantReport(id, response);
    }

    @PostMapping("/{id}/manage/places")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateExcursionPlaces(@PathVariable String id, @RequestBody UpdateExcursionPlaces update) throws NotFoundException {
        excursionService.updateExcursionPlaces(id, update.amountOfPlaces());
    }
}
