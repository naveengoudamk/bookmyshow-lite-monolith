package com.bookmyshow.service;

import com.bookmyshow.dao.BookingDAO;
import com.bookmyshow.dao.BookingSeatDAO;
import com.bookmyshow.dao.ShowDAO;
import com.bookmyshow.dao.ShowSeatDAO;
import com.bookmyshow.dto.BookingConfirmedMessage;
import com.bookmyshow.dto.BookingRequestDTO;
import com.bookmyshow.dto.BookingResponseDTO;
import com.bookmyshow.entity.Booking;
import com.bookmyshow.entity.BookingSeat;
import com.bookmyshow.entity.BookingStatus;
import com.bookmyshow.entity.SeatStatus;
import com.bookmyshow.entity.Show;
import com.bookmyshow.entity.ShowSeat;
import com.bookmyshow.exception.BookingNotFoundException;
import com.bookmyshow.exception.SeatNotAvailableException;
import com.bookmyshow.exception.ShowNotFoundException;
import com.bookmyshow.messaging.BookingConfirmedProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingDAO bookingDAO;
    private final BookingSeatDAO bookingSeatDAO;
    private final ShowDAO showDAO;
    private final ShowSeatDAO showSeatDAO;
    private final BookingConfirmedProducer bookingConfirmedProducer;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public BookingService(
            BookingDAO bookingDAO,
            BookingSeatDAO bookingSeatDAO,
            ShowDAO showDAO,
            ShowSeatDAO showSeatDAO,
            BookingConfirmedProducer bookingConfirmedProducer) {

        this.bookingDAO = bookingDAO;
        this.bookingSeatDAO = bookingSeatDAO;
        this.showDAO = showDAO;
        this.showSeatDAO = showSeatDAO;
        this.bookingConfirmedProducer = bookingConfirmedProducer;
    }


    // =========================================================
    // CONFIRM BOOKING
    // =========================================================

    @Transactional
    public BookingResponseDTO confirmBooking(
            BookingRequestDTO dto) {


        // -----------------------------------------------------
        // 1. Verify Show
        // -----------------------------------------------------

        Show show = showDAO.findById(dto.getShowId())
                .orElseThrow(() ->
                        new ShowNotFoundException(
                                dto.getShowId()
                        )
                );


        // -----------------------------------------------------
        // 2. Lock Selected Seats
        // -----------------------------------------------------

        List<ShowSeat> seats =
                showSeatDAO.findSeatsForUpdate(
                        dto.getShowId(),
                        dto.getSeatIds()
                );


        // -----------------------------------------------------
        // 3. Verify All Seats Exist
        // -----------------------------------------------------

        if (seats.size() != dto.getSeatIds().size()) {

            throw new SeatNotAvailableException(
                    "One or more seats are invalid"
            );
        }


        LocalDateTime now =
                LocalDateTime.now();


        // -----------------------------------------------------
        // 4. Validate Seat Hold
        // -----------------------------------------------------

        for (ShowSeat seat : seats) {

            // Seat must be HELD
            if (seat.getStatus() != SeatStatus.HELD) {

                throw new SeatNotAvailableException(
                        "Seat "
                                + seat.getSeatNumber()
                                + " is not held"
                );
            }


            // Hold must not be expired
            if (seat.getHoldExpiryTime() == null
                    || seat.getHoldExpiryTime().isBefore(now)) {

                throw new SeatNotAvailableException(
                        "Hold expired for seat "
                                + seat.getSeatNumber()
                );
            }


            // Same user must confirm
            if (!dto.getUserEmail()
                    .equals(seat.getHeldBy())) {

                throw new SeatNotAvailableException(
                        "Seat "
                                + seat.getSeatNumber()
                                + " is held by another user"
                );
            }
        }


        // -----------------------------------------------------
        // 5. Calculate Total Amount
        // -----------------------------------------------------

        double totalAmount =
                seats.stream()
                        .mapToDouble(ShowSeat::getPrice)
                        .sum();


        // -----------------------------------------------------
        // 6. Create Booking
        // -----------------------------------------------------

        Booking booking =
                new Booking();

        booking.setBookingRef(
                "BMS-"
                        + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        booking.setUserEmail(
                dto.getUserEmail()
        );

        booking.setShow(
                show
        );

        booking.setTotalAmount(
                totalAmount
        );

        booking.setStatus(
                BookingStatus.CONFIRMED
        );

        booking.setCreatedAt(
                now
        );


        // -----------------------------------------------------
        // 7. Save Booking
        // -----------------------------------------------------

        Booking savedBooking =
                bookingDAO.save(booking);


        // -----------------------------------------------------
        // 8. Change Seats HELD → BOOKED
        // -----------------------------------------------------

        for (ShowSeat seat : seats) {

            seat.setStatus(
                    SeatStatus.BOOKED
            );

            seat.setHeldBy(null);

            seat.setHoldExpiryTime(null);

            showSeatDAO.save(seat);


            // -------------------------------------------------
            // 9. Create BookingSeat
            // -------------------------------------------------

            BookingSeat bookingSeat =
                    new BookingSeat();

            bookingSeat.setBooking(
                    savedBooking
            );

            bookingSeat.setShowSeat(
                    seat
            );

            bookingSeatDAO.save(
                    bookingSeat
            );
        }


        // -----------------------------------------------------
        // 10. Create Booking Confirmed Message
        // -----------------------------------------------------

        BookingConfirmedMessage message = new BookingConfirmedMessage();
        message.setBookingRef( savedBooking.getBookingRef());
        message.setBookingId(savedBooking.getId());
        message.setShowId(show.getId());
        message.setSeatIds(new ArrayList<>(dto.getSeatIds()));
        message.setUserEmail(savedBooking.getUserEmail());
        message.setTotalAmount(savedBooking.getTotalAmount());


        // -----------------------------------------------------
        // 11. Send Message to RabbitMQ
        // -----------------------------------------------------

        bookingConfirmedProducer
                .sendBookingConfirmedMessage(
                        message
                );


        // -----------------------------------------------------
        // 12. Build API Response
        // -----------------------------------------------------

        BookingResponseDTO response =
                new BookingResponseDTO();

        response.setId(
                savedBooking.getId()
        );

        response.setBookingRef(
                savedBooking.getBookingRef()
        );

        response.setUserEmail(
                savedBooking.getUserEmail()
        );

        response.setShowId(
                show.getId()
        );

        response.setSeatIds(
                new ArrayList<>(dto.getSeatIds())
        );

        response.setTotalAmount(
                savedBooking.getTotalAmount()
        );

        response.setStatus(
                savedBooking.getStatus().name()
        );

        response.setCreatedAt(
                savedBooking.getCreatedAt()
        );


        return response;
    }


    // =========================================================
    // GET BOOKING BY BOOKING REFERENCE
    // =========================================================

    public BookingResponseDTO getBooking(
            String bookingRef) {

        Booking booking =
                bookingDAO.findByBookingRef(
                                bookingRef
                        )
                        .orElseThrow(() ->
                                new BookingNotFoundException(
                                        bookingRef
                                )
                        );


        // -----------------------------------------------------
        // Get Booking Seats
        // -----------------------------------------------------

        List<BookingSeat> bookingSeats =
                bookingSeatDAO.findByBookingId(
                        booking.getId()
                );


        // -----------------------------------------------------
        // Extract Show Seat IDs
        // -----------------------------------------------------

        List<Long> seatIds =
                bookingSeats.stream()
                        .map(bookingSeat ->
                                bookingSeat
                                        .getShowSeat()
                                        .getId()
                        )
                        .toList();


        // -----------------------------------------------------
        // Build Response
        // -----------------------------------------------------

        BookingResponseDTO response =
                new BookingResponseDTO();

        response.setId(
                booking.getId()
        );

        response.setBookingRef(
                booking.getBookingRef()
        );

        response.setUserEmail(
                booking.getUserEmail()
        );

        response.setShowId(
                booking.getShow().getId()
        );

        response.setSeatIds(
                seatIds
        );

        response.setTotalAmount(
                booking.getTotalAmount()
        );

        response.setStatus(
                booking.getStatus().name()
        );

        response.setCreatedAt(
                booking.getCreatedAt()
        );


        return response;
    }
}