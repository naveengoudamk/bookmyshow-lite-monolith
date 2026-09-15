package com.bookmyshow.dao;

import com.bookmyshow.entity.Screen;
import com.bookmyshow.repository.ScreenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScreenDAOImpl implements ScreenDAO {

    private final ScreenRepository screenRepository;

    public ScreenDAOImpl(ScreenRepository screenRepository){
        this.screenRepository=screenRepository;
    }

    @Override
    public Screen save(Screen screen){
        return screenRepository.save(screen);
    }

    @Override
    public List<Screen> findAll(){
        return screenRepository.findAll();
    }

    @Override
    public Optional<Screen> findById(Long id){
        return screenRepository.findById(id);
    }

    @Override
    public void deleteById(Long id){
        screenRepository.deleteById(id);
    }
}