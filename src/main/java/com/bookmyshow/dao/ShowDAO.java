package com.bookmyshow.dao;

import com.bookmyshow.entity.Show;

import java.util.List;
import java.util.Optional;

public interface ShowDAO {

    Show save(Show show);

    List<Show> findAll();

    Optional<Show> findById(Long id);

    void deleteById(Long id);
}