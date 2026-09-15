package com.bookmyshow.controller;

import com.bookmyshow.dto.BookingRequestDTO;
import com.bookmyshow.dto.BookingResponseDTO;
import com.bookmyshow.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService) {

        this.bookingService = bookingService;
    }


    // CONFIRM BOOKING
    @PostMapping("/confirm")
    public BookingResponseDTO confirmBooking(
            @Valid @RequestBody BookingRequestDTO dto) {

        return bookingService.confirmBooking(dto);
    }


    // GET BOOKING
    @GetMapping("/{bookingRef}")
    public BookingResponseDTO getBooking(
            @PathVariable String bookingRef) {

        return bookingService.getBooking(bookingRef);
    }
}