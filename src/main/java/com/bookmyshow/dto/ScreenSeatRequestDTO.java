package com.bookmyshow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ScreenSeatRequestDTO {

    @NotBlank(message = "Seat number is required")
    @Size(min = 1, max = 20,
            message = "Seat number must be between 1 and 20 characters")
    private String seatNumber;

    @NotBlank(message = "Seat type is required")
    @Size(min = 2, max = 50,
            message = "Seat type must be between 2 and 50 characters")
    private String seatType;

    @NotNull(message = "Screen ID is required")
    @Positive(message = "Screen ID must be greater than 0")
    private Long screenId;
}