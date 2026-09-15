package com.bookmyshow.controller;

import com.bookmyshow.dto.HoldSeatsRequestDTO;
import com.bookmyshow.dto.HoldSeatsResponseDTO;
import com.bookmyshow.dto.ShowSeatResponseDTO;
import com.bookmyshow.service.ShowSeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowSeatCustomerController {

    private final ShowSeatService showSeatService;


    public ShowSeatCustomerController(
            ShowSeatService showSeatService) {

        this.showSeatService = showSeatService;
    }


    // GET seats for a show
    @GetMapping("/{showId}/seats")
    public List<ShowSeatResponseDTO> getSeatsByShowId(
            @PathVariable Long showId) {

        return showSeatService.getSeatsByShowId(
                showId
        );
    }


    // Hold seats
    @PostMapping("/{showId}/hold-seats")
    public HoldSeatsResponseDTO holdSeats(
            @PathVariable Long showId,
            @Valid @RequestBody HoldSeatsRequestDTO dto) {

        return showSeatService.holdSeats(
                showId,
                dto
        );
    }
}