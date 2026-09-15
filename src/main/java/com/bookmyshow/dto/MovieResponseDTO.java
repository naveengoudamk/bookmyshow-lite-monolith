package com.bookmyshow.dto;

import lombok.Data;

@Data
public class MovieResponseDTO {

    private Long id;

    private String title;

    private String language;

    private Integer duration;
}