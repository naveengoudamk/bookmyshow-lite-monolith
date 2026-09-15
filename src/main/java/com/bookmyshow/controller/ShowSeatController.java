package com.bookmyshow.controller;

import com.bookmyshow.dto.ShowSeatRequestDTO;
import com.bookmyshow.dto.ShowSeatResponseDTO;
import com.bookmyshow.service.ShowSeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show-seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    public ShowSeatController(
            ShowSeatService showSeatService) {

        this.showSeatService = showSeatService;
    }


    // CREATE
    @PostMapping
    public ShowSeatResponseDTO createShowSeat(
            @Valid @RequestBody ShowSeatRequestDTO dto) {

        return showSeatService.createShowSeat(dto);
    }


    // GET ALL
    @GetMapping
    public List<ShowSeatResponseDTO> getAllShowSeats() {

        return showSeatService.getAllShowSeats();
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ShowSeatResponseDTO getShowSeatById(
            @PathVariable Long id) {

        return showSeatService.getShowSeatById(id);
    }


    // UPDATE
    @PutMapping("/{id}")
    public ShowSeatResponseDTO updateShowSeat(
            @PathVariable Long id,
            @Valid @RequestBody ShowSeatRequestDTO dto) {

        return showSeatService.updateShowSeat(id, dto);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public void deleteShowSeat(
            @PathVariable Long id) {

        showSeatService.deleteShowSeat(id);
    }

    @GetMapping("/show/{showId}")
    public List<ShowSeatResponseDTO> getSeatsByShowId(
            @PathVariable Long showId) {

        return showSeatService.getSeatsByShowId(showId);
    }
}