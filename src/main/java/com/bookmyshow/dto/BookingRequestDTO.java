package com.bookmyshow.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequestDTO {

    @NotNull(message = "Show ID is required")
    @Positive(message = "Show ID must be greater than 0")
    private Long showId;

    @NotEmpty(message = "Seat IDs are required")
    private List<
            @NotNull(message = "Seat ID cannot be null")
            @Positive(message = "Seat ID must be greater than 0")
                    Long> seatIds;

    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
}