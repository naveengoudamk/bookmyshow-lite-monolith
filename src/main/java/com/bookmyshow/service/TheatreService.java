package com.bookmyshow.service;

import com.bookmyshow.dao.TheatreDAO;
import com.bookmyshow.dto.TheatreRequestDTO;
import com.bookmyshow.dto.TheatreResponseDTO;
import com.bookmyshow.entity.Theatre;
import com.bookmyshow.exception.TheatreNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    private final TheatreDAO theatreDAO;

    public TheatreService(TheatreDAO theatreDAO) {
        this.theatreDAO = theatreDAO;
    }

    // CREATE
    public TheatreResponseDTO createTheatre(TheatreRequestDTO dto) {

        Theatre theatre = new Theatre();

        theatre.setName(dto.getName());
        theatre.setCity(dto.getCity());
        theatre.setAddress(dto.getAddress());

        Theatre savedTheatre = theatreDAO.save(theatre);

        return convertToResponseDTO(savedTheatre);
    }


    // GET ALL
    public List<TheatreResponseDTO> getAllTheatres() {

        return theatreDAO.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }


    // GET BY ID
    public TheatreResponseDTO getTheatreById(Long id) {

        Theatre theatre = theatreDAO.findById(id)
                .orElseThrow(() ->
                        new TheatreNotFoundException(id));

        return convertToResponseDTO(theatre);
    }


    // UPDATE
    public TheatreResponseDTO updateTheatre(
            Long id,
            TheatreRequestDTO dto) {

        Theatre existingTheatre = theatreDAO.findById(id)
                .orElseThrow(() ->
                        new TheatreNotFoundException(id));

        existingTheatre.setName(dto.getName());
        existingTheatre.setCity(dto.getCity());
        existingTheatre.setAddress(dto.getAddress());

        Theatre updatedTheatre = theatreDAO.save(existingTheatre);

        return convertToResponseDTO(updatedTheatre);
    }


    // DELETE
    public void deleteTheatre(Long id) {

        theatreDAO.findById(id)
                .orElseThrow(() ->
                        new TheatreNotFoundException(id));

        theatreDAO.deleteById(id);
    }


    // ENTITY → RESPONSE DTO
    private TheatreResponseDTO convertToResponseDTO(Theatre theatre) {

        TheatreResponseDTO dto = new TheatreResponseDTO();

        dto.setId(theatre.getId());
        dto.setName(theatre.getName());
        dto.setCity(theatre.getCity());
        dto.setAddress(theatre.getAddress());

        return dto;
    }
}