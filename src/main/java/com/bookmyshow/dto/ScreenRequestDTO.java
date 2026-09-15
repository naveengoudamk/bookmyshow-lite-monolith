package com.bookmyshow.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ScreenRequestDTO {

    @NotBlank(message = "Screen name is required")
    @Size(min = 2, max = 100)
    private String screenName;

    @NotNull
    @Positive
    private Integer totalSeats;

    @NotNull
    @Positive
    private Long theatreId;
}