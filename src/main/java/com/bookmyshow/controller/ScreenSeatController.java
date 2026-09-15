package com.bookmyshow.controller;

import com.bookmyshow.dto.ScreenSeatRequestDTO;
import com.bookmyshow.dto.ScreenSeatResponseDTO;
import com.bookmyshow.service.ScreenSeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screen-seats")
public class ScreenSeatController {

    private final ScreenSeatService screenSeatService;

    public ScreenSeatController(
            ScreenSeatService screenSeatService) {

        this.screenSeatService = screenSeatService;
    }


    // CREATE
    @PostMapping
    public ScreenSeatResponseDTO createScreenSeat(
            @Valid @RequestBody ScreenSeatRequestDTO dto) {

        return screenSeatService.createScreenSeat(dto);
    }


    // GET ALL
    @GetMapping
    public List<ScreenSeatResponseDTO> getAllScreenSeats() {

        return screenSeatService.getAllScreenSeats();
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ScreenSeatResponseDTO getScreenSeatById(
            @PathVariable Long id) {

        return screenSeatService.getScreenSeatById(id);
    }


    // UPDATE
    @PutMapping("/{id}")
    public ScreenSeatResponseDTO updateScreenSeat(
            @PathVariable Long id,
            @Valid @RequestBody ScreenSeatRequestDTO dto) {

        return screenSeatService.updateScreenSeat(id, dto);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public void deleteScreenSeat(
            @PathVariable Long id) {

        screenSeatService.deleteScreenSeat(id);
    }
}