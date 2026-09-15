package com.bookmyshow.service;

import com.bookmyshow.dao.MovieDAO;
import com.bookmyshow.dao.ScreenDAO;
import com.bookmyshow.dao.ScreenSeatDAO;
import com.bookmyshow.dao.ShowDAO;
import com.bookmyshow.dao.ShowSeatDAO;
import com.bookmyshow.dto.ShowRequestDTO;
import com.bookmyshow.dto.ShowResponseDTO;
import com.bookmyshow.entity.Movie;
import com.bookmyshow.entity.Screen;
import com.bookmyshow.entity.ScreenSeat;
import com.bookmyshow.entity.SeatStatus;
import com.bookmyshow.entity.Show;
import com.bookmyshow.entity.ShowSeat;
import com.bookmyshow.exception.MovieNotFoundException;
import com.bookmyshow.exception.ScreenNotFoundException;
import com.bookmyshow.exception.ShowNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private final ShowDAO showDAO;
    private final MovieDAO movieDAO;
    private final ScreenDAO screenDAO;
    private final ScreenSeatDAO screenSeatDAO;
    private final ShowSeatDAO showSeatDAO;

    public ShowService(
            ShowDAO showDAO,
            MovieDAO movieDAO,
            ScreenDAO screenDAO,
            ScreenSeatDAO screenSeatDAO,
            ShowSeatDAO showSeatDAO) {

        this.showDAO = showDAO;
        this.movieDAO = movieDAO;
        this.screenDAO = screenDAO;
        this.screenSeatDAO = screenSeatDAO;
        this.showSeatDAO = showSeatDAO;
    }

    // CREATE SHOW
    public ShowResponseDTO createShow(ShowRequestDTO dto) {

        // 1. Find Movie
        Movie movie = movieDAO.findById(dto.getMovieId())
                .orElseThrow(() ->
                        new MovieNotFoundException(dto.getMovieId()));

        // 2. Find Screen
        Screen screen = screenDAO.findById(dto.getScreenId())
                .orElseThrow(() ->
                        new ScreenNotFoundException(dto.getScreenId()));

        // 3. Create Show
        Show show = new Show();

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(dto.getStartTime());
        show.setEndTime(dto.getEndTime());
        show.setBasePrice(dto.getBasePrice());

        // 4. Save Show first
        Show savedShow = showDAO.save(show);

        // 5. Find physical seats of this screen
        List<ScreenSeat> screenSeats =
                screenSeatDAO.findByScreenId(screen.getId());

        // 6. Create ShowSeat for every ScreenSeat
        for (ScreenSeat screenSeat : screenSeats) {

            ShowSeat showSeat = new ShowSeat();

            showSeat.setShow(savedShow);
            showSeat.setSeatNumber(
                    screenSeat.getSeatNumber());
            showSeat.setSeatType(
                    screenSeat.getSeatType());

            showSeat.setStatus(SeatStatus.AVAILABLE);

            showSeat.setPrice(dto.getBasePrice());

            showSeatDAO.save(showSeat);
        }

        // 7. Return Show response
        return convertToResponseDTO(savedShow);
    }


    // GET ALL
    public List<ShowResponseDTO> getAllShows() {

        return showDAO.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }


    // GET BY ID
    public ShowResponseDTO getShowById(Long id) {

        Show show = showDAO.findById(id)
                .orElseThrow(() ->
                        new ShowNotFoundException(id));

        return convertToResponseDTO(show);
    }


    // UPDATE
    public ShowResponseDTO updateShow(
            Long id,
            ShowRequestDTO dto) {

        Show existingShow = showDAO.findById(id)
                .orElseThrow(() ->
                        new ShowNotFoundException(id));

        Movie movie = movieDAO.findById(dto.getMovieId())
                .orElseThrow(() ->
                        new MovieNotFoundException(dto.getMovieId()));

        Screen screen = screenDAO.findById(dto.getScreenId())
                .orElseThrow(() ->
                        new ScreenNotFoundException(dto.getScreenId()));

        existingShow.setMovie(movie);
        existingShow.setScreen(screen);
        existingShow.setStartTime(dto.getStartTime());
        existingShow.setEndTime(dto.getEndTime());
        existingShow.setBasePrice(dto.getBasePrice());

        Show updatedShow = showDAO.save(existingShow);

        return convertToResponseDTO(updatedShow);
    }


    // DELETE
    public void deleteShow(Long id) {

        showDAO.findById(id)
                .orElseThrow(() ->
                        new ShowNotFoundException(id));

        showDAO.deleteById(id);
    }


    // ENTITY → RESPONSE DTO
    private ShowResponseDTO convertToResponseDTO(Show show) {

        ShowResponseDTO dto = new ShowResponseDTO();

        dto.setId(show.getId());

        if (show.getMovie() != null) {
            dto.setMovieId(show.getMovie().getId());
            dto.setMovieTitle(show.getMovie().getTitle());
        }

        if (show.getScreen() != null) {
            dto.setScreenId(show.getScreen().getId());
            dto.setScreenName(
                    show.getScreen().getScreenName());
        }

        dto.setStartTime(show.getStartTime());
        dto.setEndTime(show.getEndTime());
        dto.setBasePrice(show.getBasePrice());

        return dto;
    }
}