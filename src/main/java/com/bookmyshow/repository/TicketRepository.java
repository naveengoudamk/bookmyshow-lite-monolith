package com.bookmyshow.repository;

import com.bookmyshow.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByTicketRef(String ticketRef);

    Optional<Ticket> findByBookingId(Long bookingId);
}