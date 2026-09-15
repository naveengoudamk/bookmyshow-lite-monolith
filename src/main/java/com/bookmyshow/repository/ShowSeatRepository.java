package com.bookmyshow.repository;

import com.bookmyshow.entity.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowSeatRepository
        extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowId(Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
       SELECT s
       FROM ShowSeat s
       WHERE s.id IN :seatIds
       AND s.show.id = :showId
       """)
    List<ShowSeat> findSeatsForUpdate(
            @Param("showId") Long showId,
            @Param("seatIds") List<Long> seatIds
    );

}
