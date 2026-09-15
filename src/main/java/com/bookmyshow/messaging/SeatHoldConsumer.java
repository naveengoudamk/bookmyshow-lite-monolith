package com.bookmyshow.messaging;

import com.bookmyshow.config.RabbitMQConfig;
import com.bookmyshow.dao.ShowSeatDAO;
import com.bookmyshow.dto.SeatHoldMessage;
import com.bookmyshow.entity.SeatStatus;
import com.bookmyshow.entity.ShowSeat;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SeatHoldConsumer {

    private final ShowSeatDAO showSeatDAO;


    public SeatHoldConsumer(
            ShowSeatDAO showSeatDAO) {

        this.showSeatDAO = showSeatDAO;
    }


    // =========================================================
    // CONSUME EXPIRED HOLD
    // =========================================================

    @RabbitListener(
            queues = RabbitMQConfig.EXPIRED_QUEUE
    )
    @Transactional
    public void releaseExpiredSeats(
            SeatHoldMessage message) {

        System.out.println(
                "Received expired seat hold message: "
                        + message
        );


        // -----------------------------------------------------
        // 1. Find seats again with database lock
        // -----------------------------------------------------

        List<ShowSeat> seats =
                showSeatDAO.findSeatsForUpdate(
                        message.getShowId(),
                        message.getSeatIds()
                );


        // -----------------------------------------------------
        // 2. Check each seat
        // -----------------------------------------------------

        LocalDateTime now =
                LocalDateTime.now();


        for (ShowSeat seat : seats) {

            // Only release if it is still HELD
            if (seat.getStatus() != SeatStatus.HELD) {
                continue;
            }


            // Make sure this is the same user
            if (!message.getUserEmail()
                    .equals(seat.getHeldBy())) {

                continue;
            }


            // Make sure this is the same hold
            if (seat.getHoldExpiryTime() == null) {
                continue;
            }


            if (!seat.getHoldExpiryTime()
                    .equals(message.getHoldExpiryTime())) {

                continue;
            }


            // -------------------------------------------------
            // 3. Check expiry
            // -------------------------------------------------

            if (seat.getHoldExpiryTime()
                    .isAfter(now)) {

                continue;
            }


            // -------------------------------------------------
            // 4. Release seat
            // -------------------------------------------------

            seat.setStatus(
                    SeatStatus.AVAILABLE
            );

            seat.setHeldBy(null);

            seat.setHoldExpiryTime(null);

            showSeatDAO.save(seat);


            System.out.println(
                    "Seat "
                            + seat.getSeatNumber()
                            + " released successfully."
            );
        }
    }
}