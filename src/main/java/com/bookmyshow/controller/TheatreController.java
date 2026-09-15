package com.bookmyshow.controller;

import com.bookmyshow.dto.TheatreRequestDTO;
import com.bookmyshow.dto.TheatreResponseDTO;
import com.bookmyshow.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }


    // CREATE THEATRE
    @PostMapping
    public TheatreResponseDTO createTheatre(
            @Valid @RequestBody TheatreRequestDTO dto) {

        return theatreService.createTheatre(dto);
    }


    // GET ALL THEATRES
    @GetMapping
    public List<TheatreResponseDTO> getAllTheatres() {

        return theatreService.getAllTheatres();
    }


    // GET THEATRE BY ID
    @GetMapping("/{id}")
    public TheatreResponseDTO getTheatreById(
            @PathVariable Long id) {

        return theatreService.getTheatreById(id);
    }


    // UPDATE THEATRE
    @PutMapping("/{id}")
    public TheatreResponseDTO updateTheatre(
            @PathVariable Long id,
            @Valid @RequestBody TheatreRequestDTO dto) {

        return theatreService.updateTheatre(id, dto);
    }


    // DELETE THEATRE
    @DeleteMapping("/{id}")
    public void deleteTheatre(
            @PathVariable Long id) {

        theatreService.deleteTheatre(id);
    }
}