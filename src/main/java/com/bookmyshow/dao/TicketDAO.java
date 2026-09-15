package com.bookmyshow.dao;

import com.bookmyshow.entity.Ticket;

import java.util.Optional;

public interface TicketDAO {

    Ticket save(Ticket ticket);

    Optional<Ticket> findById(Long id);

    Optional<Ticket> findByTicketRef(String ticketRef);

    Optional<Ticket> findByBookingId(Long bookingId);
}