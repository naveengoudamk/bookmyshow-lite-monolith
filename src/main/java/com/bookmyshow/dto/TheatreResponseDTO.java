package com.bookmyshow.dto;

import lombok.Data;

@Data
public class TheatreResponseDTO {

    private Long id;

    private String name;

    private String city;

    private String address;
}