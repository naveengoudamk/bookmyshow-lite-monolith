package com.bookmyshow.dao;

import com.bookmyshow.entity.ShowSeat;

import java.util.List;
import java.util.Optional;

public interface ShowSeatDAO {

    ShowSeat save(ShowSeat showSeat);

    List<ShowSeat> findAll();

    Optional<ShowSeat> findById(Long id);

    void deleteById(Long id);

    List<ShowSeat> findByShowId(Long showId);

    List<ShowSeat> findSeatsForUpdate(
            Long showId,
            List<Long> seatIds
    );
}