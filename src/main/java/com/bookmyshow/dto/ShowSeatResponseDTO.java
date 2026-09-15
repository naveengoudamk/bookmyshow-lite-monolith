package com.bookmyshow.dto;

import com.bookmyshow.entity.SeatStatus;
import lombok.Data;

@Data
public class ShowSeatResponseDTO {

    private Long id;

    private Long showId;

    private String seatNumber;

    private String seatType;

    private SeatStatus status;

    private Double price;

    private Long version;
}