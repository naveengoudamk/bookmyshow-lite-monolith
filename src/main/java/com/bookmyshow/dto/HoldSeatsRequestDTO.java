package com.bookmyshow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class HoldSeatsRequestDTO {

    @NotEmpty(message = "Seat IDs are required")
    private List<
            @NotNull(message = "Seat ID cannot be null")
            @Positive(message = "Seat ID must be greater than 0")
                    Long> seatIds;

    @NotNull(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
}