package com.bookmyshow.dto;

import lombok.Data;

@Data
public class ScreenSeatResponseDTO {

    private Long id;

    private String seatNumber;

    private String seatType;

    private Long screenId;

    private String screenName;
}