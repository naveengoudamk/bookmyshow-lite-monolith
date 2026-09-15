package com.bookmyshow.dao;

import com.bookmyshow.entity.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreDAO {

    Theatre save(Theatre theatre);

    List<Theatre> findAll();

    Optional<Theatre> findById(Long id);

    void deleteById(Long id);
}