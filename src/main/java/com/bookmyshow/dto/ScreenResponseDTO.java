package com.bookmyshow.dto;

import lombok.Data;

@Data
public class ScreenResponseDTO {

    private Long id;

    private String screenName;

    private Integer totalSeats;

    private Long theatreId;

    private String theatreName;
}