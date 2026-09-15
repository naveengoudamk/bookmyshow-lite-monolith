package com.bookmyshow.service;

import com.bookmyshow.dao.ScreenDAO;
import com.bookmyshow.dao.ScreenSeatDAO;
import com.bookmyshow.dto.ScreenSeatRequestDTO;
import com.bookmyshow.dto.ScreenSeatResponseDTO;
import com.bookmyshow.entity.Screen;
import com.bookmyshow.entity.ScreenSeat;
import com.bookmyshow.exception.ScreenNotFoundException;
import com.bookmyshow.exception.ScreenSeatNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ScreenSeatService {

    private final ScreenSeatDAO screenSeatDAO;
    private final ScreenDAO screenDAO;

    public ScreenSeatService(
            ScreenSeatDAO screenSeatDAO,
            ScreenDAO screenDAO) {

        this.screenSeatDAO = screenSeatDAO;
        this.screenDAO = screenDAO;
    }

    // CREATE
    public ScreenSeatResponseDTO createScreenSeat(
            ScreenSeatRequestDTO dto) {

        Screen screen = screenDAO.findById(dto.getScreenId())
                .orElseThrow(() ->
                        new ScreenNotFoundException(dto.getScreenId()));

        ScreenSeat screenSeat = new ScreenSeat();

        screenSeat.setSeatNumber(dto.getSeatNumber());
        screenSeat.setSeatType(dto.getSeatType());
        screenSeat.setScreen(screen);

        ScreenSeat savedSeat = screenSeatDAO.save(screenSeat);

        return convertToResponseDTO(savedSeat);
    }


    // GET ALL
    public List<ScreenSeatResponseDTO> getAllScreenSeats() {

        return screenSeatDAO.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }


    // GET BY ID
    public ScreenSeatResponseDTO getScreenSeatById(Long id) {

        ScreenSeat screenSeat = screenSeatDAO.findById(id)
                .orElseThrow(() ->
                        new ScreenSeatNotFoundException(id));

        return convertToResponseDTO(screenSeat);
    }


    // UPDATE
    public ScreenSeatResponseDTO updateScreenSeat(
            Long id,
            ScreenSeatRequestDTO dto) {

        ScreenSeat existingSeat = screenSeatDAO.findById(id)
                .orElseThrow(() ->
                        new ScreenSeatNotFoundException(id));

        Screen screen = screenDAO.findById(dto.getScreenId())
                .orElseThrow(() ->
                        new ScreenNotFoundException(dto.getScreenId()));

        existingSeat.setSeatNumber(dto.getSeatNumber());
        existingSeat.setSeatType(dto.getSeatType());
        existingSeat.setScreen(screen);

        ScreenSeat updatedSeat =
                screenSeatDAO.save(existingSeat);

        return convertToResponseDTO(updatedSeat);
    }


    // DELETE
    public void deleteScreenSeat(Long id) {

        screenSeatDAO.findById(id)
                .orElseThrow(() ->
                        new ScreenSeatNotFoundException(id));

        screenSeatDAO.deleteById(id);
    }


    // ENTITY → RESPONSE DTO
    private ScreenSeatResponseDTO convertToResponseDTO(
            ScreenSeat screenSeat) {

        ScreenSeatResponseDTO dto =
                new ScreenSeatResponseDTO();

        dto.setId(screenSeat.getId());
        dto.setSeatNumber(screenSeat.getSeatNumber());
        dto.setSeatType(screenSeat.getSeatType());

        if (screenSeat.getScreen() != null) {
            dto.setScreenId(screenSeat.getScreen().getId());
            dto.setScreenName(
                    screenSeat.getScreen().getScreenName());
        }

        return dto;
    }
}