package com.bookmyshow.controller;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // insert into database
    @PostMapping
    public MovieResponseDTO createMovie( @Valid @RequestBody MovieRequestDTO dto) {

        return movieService.createMovie(dto);
    }

    // get all the data from db
    @GetMapping
    public List<MovieResponseDTO> getAllMovies() {

        return movieService.getAllMovies();
    }

    // by name of the movie seraching
    @GetMapping("/search")
    public List<MovieResponseDTO> searchMovies(
            @RequestParam String title) {

        return movieService.searchMoviesByTitle(title);
    }

    // getting by id
    @GetMapping("/{id}")
    public MovieResponseDTO getMovieById(
            @PathVariable Long id) {

        return movieService.getMovieById(id);
    }

    // updating the data
    @PutMapping("/{id}")
    public MovieResponseDTO updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequestDTO dto) {

        return movieService.updateMovie(id, dto);
    }

    // delete the data by id
    @DeleteMapping("/{id}")
    public void deleteMovie(
            @PathVariable Long id) {

        movieService.deleteMovie(id);
    }
}