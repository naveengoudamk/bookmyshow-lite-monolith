package com.bookmyshow.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponseDTO {

    private Long id;

    private String bookingRef;

    private String userEmail;

    private Long showId;

    private List<Long> seatIds;

    private Double totalAmount;

    private String status;

    private LocalDateTime createdAt;
}