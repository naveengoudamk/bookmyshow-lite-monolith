package com.bookmyshow.dao;

import com.bookmyshow.entity.ShowSeat;
import com.bookmyshow.repository.ShowSeatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShowSeatDAOImpl implements ShowSeatDAO {

    private final ShowSeatRepository showSeatRepository;

    public ShowSeatDAOImpl(
            ShowSeatRepository showSeatRepository) {

        this.showSeatRepository = showSeatRepository;
    }

    @Override
    public ShowSeat save(ShowSeat showSeat) {
        return showSeatRepository.save(showSeat);
    }

    @Override
    public List<ShowSeat> findAll() {
        return showSeatRepository.findAll();
    }

    @Override
    public Optional<ShowSeat> findById(Long id) {
        return showSeatRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        showSeatRepository.deleteById(id);
    }

    @Override
    public List<ShowSeat> findByShowId(Long showId) {
        return showSeatRepository.findByShowId(showId);
    }

    @Override
    public List<ShowSeat> findSeatsForUpdate(
            Long showId,
            List<Long> seatIds) {

        return showSeatRepository.findSeatsForUpdate(
                showId,
                seatIds
        );
    }


}