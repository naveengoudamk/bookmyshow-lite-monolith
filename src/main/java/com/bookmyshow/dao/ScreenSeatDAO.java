package com.bookmyshow.dao;

import com.bookmyshow.entity.ScreenSeat;

import java.util.List;
import java.util.Optional;

public interface ScreenSeatDAO {

    ScreenSeat save(ScreenSeat screenSeat);

    List<ScreenSeat> findAll();

    Optional<ScreenSeat> findById(Long id);

    void deleteById(Long id);

    List<ScreenSeat> findByScreenId(Long screenId);
}