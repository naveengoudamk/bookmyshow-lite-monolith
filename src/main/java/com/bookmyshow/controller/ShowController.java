package com.bookmyshow.controller;

import com.bookmyshow.dto.ShowRequestDTO;
import com.bookmyshow.dto.ShowResponseDTO;
import com.bookmyshow.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }


    // CREATE
    @PostMapping
    public ShowResponseDTO createShow(
            @Valid @RequestBody ShowRequestDTO dto) {

        return showService.createShow(dto);
    }


    // GET ALL
    @GetMapping
    public List<ShowResponseDTO> getAllShows() {

        return showService.getAllShows();
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ShowResponseDTO getShowById(
            @PathVariable Long id) {

        return showService.getShowById(id);
    }


    // UPDATE
    @PutMapping("/{id}")
    public ShowResponseDTO updateShow(
            @PathVariable Long id,
            @Valid @RequestBody ShowRequestDTO dto) {

        return showService.updateShow(id, dto);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public void deleteShow(
            @PathVariable Long id) {

        showService.deleteShow(id);
    }
}