package com.bookmyshow.service;

import com.bookmyshow.dao.ScreenDAO;
import com.bookmyshow.dao.TheatreDAO;
import com.bookmyshow.dto.ScreenRequestDTO;
import com.bookmyshow.dto.ScreenResponseDTO;
import com.bookmyshow.entity.Screen;
import com.bookmyshow.entity.Theatre;
import com.bookmyshow.exception.ScreenNotFoundException;
import com.bookmyshow.exception.TheatreNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    private final ScreenDAO screenDAO;
    private final TheatreDAO theatreDAO;

    public ScreenService(ScreenDAO screenDAO, TheatreDAO theatreDAO){
        this.screenDAO=screenDAO;
        this.theatreDAO=theatreDAO;
    }

    public ScreenResponseDTO createScreen(ScreenRequestDTO dto){

        Theatre theatre=theatreDAO.findById(dto.getTheatreId())
                .orElseThrow(()->new TheatreNotFoundException(dto.getTheatreId()));

        Screen screen=new Screen();

        screen.setScreenName(dto.getScreenName());
        screen.setTotalSeats(dto.getTotalSeats());
        screen.setTheatre(theatre);

        return convertToDTO(screenDAO.save(screen));
    }

    public List<ScreenResponseDTO> getAllScreens(){

        return screenDAO.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ScreenResponseDTO getScreenById(Long id){

        Screen screen=screenDAO.findById(id)
                .orElseThrow(()->new ScreenNotFoundException(id));

        return convertToDTO(screen);
    }

    public ScreenResponseDTO updateScreen(Long id,ScreenRequestDTO dto){

        Screen screen=screenDAO.findById(id)
                .orElseThrow(()->new ScreenNotFoundException(id));

        Theatre theatre=theatreDAO.findById(dto.getTheatreId())
                .orElseThrow(()->new TheatreNotFoundException(dto.getTheatreId()));

        screen.setScreenName(dto.getScreenName());
        screen.setTotalSeats(dto.getTotalSeats());
        screen.setTheatre(theatre);

        return convertToDTO(screenDAO.save(screen));
    }

    public void deleteScreen(Long id){

        screenDAO.findById(id)
                .orElseThrow(()->new ScreenNotFoundException(id));

        screenDAO.deleteById(id);
    }

    private ScreenResponseDTO convertToDTO(Screen screen){

        ScreenResponseDTO dto=new ScreenResponseDTO();

        dto.setId(screen.getId());
        dto.setScreenName(screen.getScreenName());
        dto.setTotalSeats(screen.getTotalSeats());
        dto.setTheatreId(screen.getTheatre().getId());
        dto.setTheatreName(screen.getTheatre().getName());

        return dto;
    }
}