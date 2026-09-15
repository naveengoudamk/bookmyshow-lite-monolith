package com.bookmyshow.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowRequestDTO {

    @NotNull(message = "Movie ID is required")
    @Positive(message = "Movie ID must be greater than 0")
    private Long movieId;

    @NotNull(message = "Screen ID is required")
    @Positive(message = "Screen ID must be greater than 0")
    private Long screenId;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be greater than 0")
    private Double basePrice;
}