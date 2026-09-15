package com.bookmyshow.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingConfirmedMessage {

    private String bookingRef;

    private Long bookingId;

    private Long showId;

    private List<Long> seatIds;

    private String userEmail;

    private Double totalAmount;
}