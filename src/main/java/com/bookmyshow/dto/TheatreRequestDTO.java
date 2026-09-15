package com.bookmyshow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TheatreRequestDTO {

    @NotBlank(message = "Theatre name is required")
    @Size(
            min = 2,
            max = 200,
            message = "Theatre name must be between 2 and 200 characters"
    )
    private String name;

    @NotBlank(message = "City is required")
    @Size(
            min = 2,
            max = 100,
            message = "City must be between 2 and 100 characters"
    )
    private String city;

    @NotBlank(message = "Address is required")
    @Size(
            min = 5,
            max = 300,
            message = "Address must be between 5 and 300 characters"
    )
    private String address;
}