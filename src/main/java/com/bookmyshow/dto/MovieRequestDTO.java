package com.bookmyshow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MovieRequestDTO {

    @NotBlank(message = "Movie title is required")
    @Size(min = 2, max = 200, message = "Movie title must be between 2 and 200 characters")
    private String title;

    @NotBlank(message = "Language is required")
    @Size(min = 2, max = 50, message = "Language must be between 2 and 50 characters")
    private String language;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;
}