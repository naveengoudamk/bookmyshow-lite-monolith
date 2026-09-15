package com.bookmyshow.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ShowSeatRequestDTO {

    @NotBlank(message = "Seat number is required")
    @Size(min = 1, max = 20,
            message = "Seat number must be between 1 and 20 characters")
    private String seatNumber;

    @NotBlank(message = "Seat type is required")
    @Size(min = 2, max = 50,
            message = "Seat type must be between 2 and 50 characters")
    private String seatType;

    @NotNull(message = "Show ID is required")
    @Positive(message = "Show ID must be greater than 0")
    private Long showId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;
}