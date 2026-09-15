package com.bookmyshow.dao;

import com.bookmyshow.entity.Movie;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDAOImpl implements MovieDAO {

    private final MovieRepository movieRepository;

    public MovieDAOImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> findByTitleContainingIgnoreCase(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
}