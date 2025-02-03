package com.checkout.coding.api.controllers;

import com.checkout.coding.api.dto.BookingRequest;
import com.checkout.coding.api.entities.Booking;
import com.checkout.coding.api.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> saveBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
}
