package com.bookmyshow.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SeatHoldMessage {

    private Long showId;

    private List<Long> seatIds;

    private String userEmail;

    private LocalDateTime holdExpiryTime;
}