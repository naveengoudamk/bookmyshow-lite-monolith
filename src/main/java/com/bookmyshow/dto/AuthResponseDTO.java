package com.bookmyshow.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String token;
    private String tokenType;
    private Long userId;
    private String name;
    private String email;
    private String role;
}