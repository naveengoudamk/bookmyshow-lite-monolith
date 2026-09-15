package com.bookmyshow.controller;

import com.bookmyshow.dto.AuthResponseDTO;
import com.bookmyshow.dto.LoginRequestDTO;
import com.bookmyshow.dto.RegisterRequestDTO;
import com.bookmyshow.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO dto) {

        AuthResponseDTO response =
                authService.register(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        AuthResponseDTO response =
                authService.login(dto);

        return ResponseEntity.ok(response);
    }
}