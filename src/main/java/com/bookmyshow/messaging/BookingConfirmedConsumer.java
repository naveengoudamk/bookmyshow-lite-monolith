package com.bookmyshow.messaging;

import com.bookmyshow.config.RabbitMQConfig;
import com.bookmyshow.dao.BookingDAO;
import com.bookmyshow.dao.TicketDAO;
import com.bookmyshow.dto.BookingConfirmedMessage;
import com.bookmyshow.entity.Booking;
import com.bookmyshow.entity.Ticket;
import com.bookmyshow.entity.TicketStatus;
import com.bookmyshow.service.QRCodeService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BookingConfirmedConsumer {

    private final BookingDAO bookingDAO;

    private final TicketDAO ticketDAO;

    private final QRCodeService qrCodeService;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public BookingConfirmedConsumer(
            BookingDAO bookingDAO,
            TicketDAO ticketDAO,
            QRCodeService qrCodeService) {

        this.bookingDAO = bookingDAO;

        this.ticketDAO = ticketDAO;

        this.qrCodeService = qrCodeService;
    }


    // =========================================================
    // RABBITMQ CONSUMER
    // =========================================================

    @RabbitListener(
            queues =
                    RabbitMQConfig.BOOKING_CONFIRMED_QUEUE
    )
    @Transactional
    public void consumeBookingConfirmed(
            BookingConfirmedMessage message) {


        System.out.println(
                "======================================"
        );

        System.out.println(
                "Received booking.confirmed message"
        );

        System.out.println(
                "Booking ID: "
                        + message.getBookingId()
        );

        System.out.println(
                "Booking Ref: "
                        + message.getBookingRef()
        );


        // -----------------------------------------------------
        // 1. Find Booking
        // -----------------------------------------------------

        Booking booking =
                bookingDAO.findById(
                        message.getBookingId()
                ).orElse(null);


        if (booking == null) {

            System.out.println(
                    "Booking not found: "
                            + message.getBookingId()
            );

            return;
        }


        // -----------------------------------------------------
        // 2. Prevent Duplicate Ticket
        // -----------------------------------------------------

        if (ticketDAO.findByBookingId(
                booking.getId()
        ).isPresent()) {

            System.out.println(
                    "Ticket already exists for booking: "
                            + booking.getBookingRef()
            );

            return;
        }


        // -----------------------------------------------------
        // 3. Generate Ticket Reference
        // -----------------------------------------------------

        String ticketRef =
                "TKT-"
                        + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();


        // -----------------------------------------------------
        // 4. Generate QR Code
        // -----------------------------------------------------

        String qrPath;

        try {

            qrPath =
                    qrCodeService.generateQRCode(
                            booking.getBookingRef(),
                            booking.getUserEmail(),
                            booking.getTotalAmount()
                    );

        } catch (Exception e) {

            System.out.println(
                    "QR code generation failed"
            );

            e.printStackTrace();

            return;
        }


        // -----------------------------------------------------
        // 5. Create Ticket
        // -----------------------------------------------------

        Ticket ticket =
                new Ticket();

        ticket.setTicketRef(
                ticketRef
        );

        ticket.setBooking(
                booking
        );

        ticket.setTicketUrl(
                qrPath
        );

        ticket.setStatus(
                TicketStatus.GENERATED
        );

        ticket.setGeneratedAt(
                LocalDateTime.now()
        );


        // -----------------------------------------------------
        // 6. Save Ticket
        // -----------------------------------------------------

        Ticket savedTicket =
                ticketDAO.save(
                        ticket
                );


        // -----------------------------------------------------
        // 7. Print Result
        // -----------------------------------------------------

        System.out.println(
                "Ticket generated successfully!"
        );

        System.out.println(
                "Ticket Ref: "
                        + savedTicket.getTicketRef()
        );

        System.out.println(
                "QR Path: "
                        + savedTicket.getTicketUrl()
        );

        System.out.println(
                "======================================"
        );
    }
}