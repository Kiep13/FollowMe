package com.cyberapple.followme.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.records.BookingInput;
import com.cyberapple.followme.services.BookingService;

import io.micrometer.core.annotation.Counted;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/bookings")
@AllArgsConstructor
public class ApiBookingController {

    private final BookingService bookingService;

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Iterable<Participation> getBookedExcursions() {
        return bookingService.getBookedExcursions();
    }

    @PostMapping("/{id}/add")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Counted(value = "api.calls.booking", description = "Number of calls to /api/{excursionId}/add")
    public void registerForExcursion(@PathVariable String id, @RequestBody BookingInput bookingInput) {
        bookingService.registerForExcursion(id, bookingInput);
    }
}
