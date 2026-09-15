package com.bookmyshow.service;

import com.bookmyshow.dao.ShowDAO;
import com.bookmyshow.dao.ShowSeatDAO;
import com.bookmyshow.dto.HoldSeatsRequestDTO;
import com.bookmyshow.dto.HoldSeatsResponseDTO;
import com.bookmyshow.dto.SeatHoldMessage;
import com.bookmyshow.dto.ShowSeatRequestDTO;
import com.bookmyshow.dto.ShowSeatResponseDTO;
import com.bookmyshow.entity.SeatStatus;
import com.bookmyshow.entity.Show;
import com.bookmyshow.entity.ShowSeat;
import com.bookmyshow.exception.SeatNotAvailableException;
import com.bookmyshow.exception.ShowNotFoundException;
import com.bookmyshow.exception.ShowSeatNotFoundException;
import com.bookmyshow.messaging.SeatHoldProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowSeatService {

    private final ShowSeatDAO showSeatDAO;

    private final ShowDAO showDAO;

    private final SeatHoldProducer seatHoldProducer;


    public ShowSeatService(
            ShowSeatDAO showSeatDAO,
            ShowDAO showDAO,
            SeatHoldProducer seatHoldProducer) {

        this.showSeatDAO = showSeatDAO;
        this.showDAO = showDAO;
        this.seatHoldProducer = seatHoldProducer;
    }


    // =========================================================
    // CREATE SHOW SEAT
    // =========================================================

    public ShowSeatResponseDTO createShowSeat(
            ShowSeatRequestDTO dto) {

        Show show = showDAO.findById(dto.getShowId())
                .orElseThrow(
                        () -> new ShowNotFoundException(
                                dto.getShowId()
                        )
                );

        ShowSeat showSeat = new ShowSeat();

        showSeat.setShow(show);
        showSeat.setSeatNumber(dto.getSeatNumber());
        showSeat.setSeatType(dto.getSeatType());
        showSeat.setStatus(SeatStatus.AVAILABLE);
        showSeat.setPrice(dto.getPrice());

        ShowSeat savedSeat =
                showSeatDAO.save(showSeat);

        return mapToResponse(savedSeat);
    }


    // =========================================================
    // GET ALL SHOW SEATS
    // =========================================================

    public List<ShowSeatResponseDTO> getAllShowSeats() {

        List<ShowSeat> seats =
                showSeatDAO.findAll();

        List<ShowSeatResponseDTO> response =
                new ArrayList<>();

        for (ShowSeat seat : seats) {
            response.add(
                    mapToResponse(seat)
            );
        }

        return response;
    }


    // =========================================================
    // GET SHOW SEAT BY ID
    // =========================================================

    public ShowSeatResponseDTO getShowSeatById(
            Long id) {

        ShowSeat showSeat =
                showSeatDAO.findById(id)
                        .orElseThrow(
                                () -> new ShowSeatNotFoundException(id)
                        );

        return mapToResponse(showSeat);
    }


    // =========================================================
    // GET SEATS BY SHOW
    // =========================================================

    public List<ShowSeatResponseDTO> getSeatsByShowId(
            Long showId) {

        showDAO.findById(showId)
                .orElseThrow(
                        () -> new ShowNotFoundException(showId)
                );

        List<ShowSeat> seats =
                showSeatDAO.findByShowId(showId);

        List<ShowSeatResponseDTO> response =
                new ArrayList<>();

        for (ShowSeat seat : seats) {
            response.add(
                    mapToResponse(seat)
            );
        }

        return response;
    }


    // =========================================================
    // UPDATE SHOW SEAT
    // =========================================================

    public ShowSeatResponseDTO updateShowSeat(
            Long id,
            ShowSeatRequestDTO dto) {

        ShowSeat showSeat =
                showSeatDAO.findById(id)
                        .orElseThrow(
                                () -> new ShowSeatNotFoundException(id)
                        );

        Show show =
                showDAO.findById(dto.getShowId())
                        .orElseThrow(
                                () -> new ShowNotFoundException(
                                        dto.getShowId()
                                )
                        );

        showSeat.setShow(show);
        showSeat.setSeatNumber(dto.getSeatNumber());
        showSeat.setSeatType(dto.getSeatType());
        showSeat.setPrice(dto.getPrice());

        ShowSeat updatedSeat =
                showSeatDAO.save(showSeat);

        return mapToResponse(updatedSeat);
    }


    // =========================================================
    // DELETE SHOW SEAT
    // =========================================================

    public void deleteShowSeat(Long id) {

        showSeatDAO.findById(id)
                .orElseThrow(
                        () -> new ShowSeatNotFoundException(id)
                );

        showSeatDAO.deleteById(id);
    }


    // =========================================================
    // HOLD SEATS
    // =========================================================

    @Transactional
    public HoldSeatsResponseDTO holdSeats(
            Long showId,
            HoldSeatsRequestDTO dto) {

        // -----------------------------------------------------
        // 1. Verify show
        // -----------------------------------------------------

        showDAO.findById(showId)
                .orElseThrow(
                        () -> new ShowNotFoundException(showId)
                );


        // -----------------------------------------------------
        // 2. Lock selected seats
        // -----------------------------------------------------

        List<ShowSeat> seats =
                showSeatDAO.findSeatsForUpdate(
                        showId,
                        dto.getSeatIds()
                );


        // -----------------------------------------------------
        // 3. Verify requested seats
        // -----------------------------------------------------

        if (seats.size() != dto.getSeatIds().size()) {

            throw new SeatNotAvailableException(
                    "One or more selected seats do not belong to this show"
            );
        }


        LocalDateTime now =
                LocalDateTime.now();


        // -----------------------------------------------------
        // 4. Check availability
        // -----------------------------------------------------

        for (ShowSeat seat : seats) {

            if (seat.getStatus() == SeatStatus.HELD
                    && seat.getHoldExpiryTime() != null
                    && seat.getHoldExpiryTime().isBefore(now)) {

                seat.setStatus(
                        SeatStatus.AVAILABLE
                );

                seat.setHeldBy(null);

                seat.setHoldExpiryTime(null);
            }


            if (seat.getStatus() != SeatStatus.AVAILABLE) {

                throw new SeatNotAvailableException(
                        "Seat "
                                + seat.getSeatNumber()
                                + " is not available"
                );
            }
        }


        // -----------------------------------------------------
        // 5. Create 5-minute expiry time
        // -----------------------------------------------------

        LocalDateTime expiryTime =
                now.plusMinutes(5);


        // -----------------------------------------------------
        // 6. Change seats to HELD
        // -----------------------------------------------------

        for (ShowSeat seat : seats) {

            seat.setStatus(
                    SeatStatus.HELD
            );

            seat.setHeldBy(
                    dto.getUserEmail()
            );

            seat.setHoldExpiryTime(
                    expiryTime
            );

            showSeatDAO.save(seat);
        }


        // -----------------------------------------------------
        // 7. Create RabbitMQ message
        // -----------------------------------------------------

        SeatHoldMessage message =
                new SeatHoldMessage();

        message.setShowId(
                showId
        );

        message.setSeatIds(
                dto.getSeatIds()
        );

        message.setUserEmail(
                dto.getUserEmail()
        );

        message.setHoldExpiryTime(
                expiryTime
        );


        // -----------------------------------------------------
        // 8. Send RabbitMQ message
        // -----------------------------------------------------

        seatHoldProducer.sendHoldExpiryMessage(
                message
        );


        // -----------------------------------------------------
        // 9. API response
        // -----------------------------------------------------

        HoldSeatsResponseDTO response =
                new HoldSeatsResponseDTO();

        response.setShowId(
                showId
        );

        response.setUserEmail(
                dto.getUserEmail()
        );

        response.setSeatIds(
                dto.getSeatIds()
        );

        response.setStatus(
                "HELD"
        );

        response.setHoldExpiryTime(
                expiryTime
        );

        return response;
    }


    // =========================================================
    // ENTITY → RESPONSE DTO
    // =========================================================

    private ShowSeatResponseDTO mapToResponse(
            ShowSeat showSeat) {

        ShowSeatResponseDTO response =
                new ShowSeatResponseDTO();

        response.setId(
                showSeat.getId()
        );

        response.setShowId(
                showSeat.getShow().getId()
        );

        response.setSeatNumber(
                showSeat.getSeatNumber()
        );

        response.setSeatType(
                showSeat.getSeatType()
        );

        response.setStatus(
                showSeat.getStatus()
        );

        response.setPrice(
                showSeat.getPrice()
        );

        response.setVersion(
                showSeat.getVersion()
        );

        return response;
    }
}