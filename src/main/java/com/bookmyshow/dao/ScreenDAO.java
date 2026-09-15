package com.bookmyshow.dao;

import com.bookmyshow.entity.Screen;

import java.util.List;
import java.util.Optional;

public interface ScreenDAO {

    Screen save(Screen screen);

    List<Screen> findAll();

    Optional<Screen> findById(Long id);

    void deleteById(Long id);
}