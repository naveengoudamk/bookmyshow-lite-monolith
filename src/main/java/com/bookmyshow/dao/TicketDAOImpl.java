package com.bookmyshow.dao;

import com.bookmyshow.entity.Ticket;
import com.bookmyshow.repository.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TicketDAOImpl implements TicketDAO {

    private final TicketRepository ticketRepository;

    public TicketDAOImpl(
            TicketRepository ticketRepository) {

        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {

        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findById(Long id) {

        return ticketRepository.findById(id);
    }

    @Override
    public Optional<Ticket> findByTicketRef(
            String ticketRef) {

        return ticketRepository.findByTicketRef(ticketRef);
    }

    @Override
    public Optional<Ticket> findByBookingId(
            Long bookingId) {

        return ticketRepository.findByBookingId(
                bookingId
        );
    }
}