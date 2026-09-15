package com.bookmyshow.dao;

import com.bookmyshow.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDAO {

    Movie save(Movie movie);

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    void deleteById(Long id);

    List<Movie> findByTitleContainingIgnoreCase(String title);
}