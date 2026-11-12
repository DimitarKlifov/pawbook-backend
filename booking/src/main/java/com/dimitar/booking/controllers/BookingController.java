package com.dimitar.booking.controllers;

import com.dimitar.booking.dto.AvailabilityResponse;
import com.dimitar.booking.dto.BookingRequest;
import com.dimitar.booking.dto.BookingResponse;
import com.dimitar.booking.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/availability")
    public ResponseEntity<AvailabilityResponse> getAvailability(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
                                                                @RequestParam Long serviceId,
                                                                @RequestParam Long petId){
        return ResponseEntity.ok(bookingService.getAvailability(date, serviceId, petId));
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request){
        BookingResponse response = bookingService.createBooking(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/bookings/mine")
    public ResponseEntity<List<BookingResponse>> getMyBookings(){
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}
