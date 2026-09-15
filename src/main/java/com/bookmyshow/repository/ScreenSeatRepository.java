package com.bookmyshow.repository;

import com.bookmyshow.entity.ScreenSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenSeatRepository
        extends JpaRepository<ScreenSeat, Long> {

    List<ScreenSeat> findByScreenId(Long screenId);
}