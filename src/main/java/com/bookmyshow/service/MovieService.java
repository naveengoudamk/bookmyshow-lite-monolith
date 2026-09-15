package com.bookmyshow.service;

import com.bookmyshow.dao.MovieDAO;
import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.entity.Movie;
import com.bookmyshow.exception.MovieNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    // CREATE
    public MovieResponseDTO createMovie(MovieRequestDTO dto) {

        Movie movie = new Movie();

        movie.setTitle(dto.getTitle());
        movie.setLanguage(dto.getLanguage());
        movie.setDuration(dto.getDuration());

        Movie savedMovie = movieDAO.save(movie);

        return convertToResponseDTO(savedMovie);
    }

    // GET ALL
    public List<MovieResponseDTO> getAllMovies() {

        return movieDAO.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // GET BY ID
    public MovieResponseDTO getMovieById(Long id) {

        Movie movie = movieDAO.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        return convertToResponseDTO(movie);
    }

    // UPDATE
    public MovieResponseDTO updateMovie(
            Long id,
            MovieRequestDTO dto) {

        Movie existingMovie = movieDAO.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        existingMovie.setTitle(dto.getTitle());
        existingMovie.setLanguage(dto.getLanguage());
        existingMovie.setDuration(dto.getDuration());

        Movie updatedMovie = movieDAO.save(existingMovie);

        return convertToResponseDTO(updatedMovie);
    }

    // DELETE
    public void deleteMovie(Long id) {

        movieDAO.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        movieDAO.deleteById(id);
    }

    // SEARCH BY MOVIE NAME
    public List<MovieResponseDTO> searchMoviesByTitle(String title) {

        return movieDAO.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // ENTITY → RESPONSE DTO
    private MovieResponseDTO convertToResponseDTO(Movie movie) {

        MovieResponseDTO dto = new MovieResponseDTO();

        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setLanguage(movie.getLanguage());
        dto.setDuration(movie.getDuration());

        return dto;
    }
}