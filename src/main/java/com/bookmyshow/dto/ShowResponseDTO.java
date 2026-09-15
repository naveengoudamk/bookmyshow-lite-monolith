package com.bookmyshow.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowResponseDTO {

    private Long id;

    private Long movieId;

    private String movieTitle;

    private Long screenId;

    private String screenName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double basePrice;
}