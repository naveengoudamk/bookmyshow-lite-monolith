package com.bookmyshow.dao;

import com.bookmyshow.entity.Theatre;
import com.bookmyshow.repository.TheatreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TheatreDAOImpl implements TheatreDAO {

    private final TheatreRepository theatreRepository;

    public TheatreDAOImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public Theatre save(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    @Override
    public List<Theatre> findAll() {
        return theatreRepository.findAll();
    }

    @Override
    public Optional<Theatre> findById(Long id) {
        return theatreRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        theatreRepository.deleteById(id);
    }
}