package com.bookmyshow.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HoldSeatsResponseDTO {

    private Long showId;

    private String userEmail;

    private List<Long> seatIds;

    private String status;

    private LocalDateTime holdExpiryTime;
}