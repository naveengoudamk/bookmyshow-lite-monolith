package com.bookmyshow.dao;

import com.bookmyshow.entity.Show;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShowDAOImpl implements ShowDAO {

    private final ShowRepository showRepository;

    public ShowDAOImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public Show save(Show show) {
        return showRepository.save(show);
    }

    @Override
    public List<Show> findAll() {
        return showRepository.findAll();
    }

    @Override
    public Optional<Show> findById(Long id) {
        return showRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        showRepository.deleteById(id);
    }
}