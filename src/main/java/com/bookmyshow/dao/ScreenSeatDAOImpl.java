package com.bookmyshow.dao;

import com.bookmyshow.entity.ScreenSeat;
import com.bookmyshow.repository.ScreenSeatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScreenSeatDAOImpl implements ScreenSeatDAO {

    private final ScreenSeatRepository screenSeatRepository;

    public ScreenSeatDAOImpl(
            ScreenSeatRepository screenSeatRepository) {

        this.screenSeatRepository = screenSeatRepository;
    }

    @Override
    public ScreenSeat save(ScreenSeat screenSeat) {
        return screenSeatRepository.save(screenSeat);
    }

    @Override
    public List<ScreenSeat> findAll() {
        return screenSeatRepository.findAll();
    }

    @Override
    public Optional<ScreenSeat> findById(Long id) {
        return screenSeatRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        screenSeatRepository.deleteById(id);
    }

    @Override
    public List<ScreenSeat> findByScreenId(Long screenId) {
        return screenSeatRepository.findByScreenId(screenId);
    }
}