package com.bookmyshow.dao;

import com.bookmyshow.entity.Booking;
import com.bookmyshow.repository.BookingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookingDAOImpl implements BookingDAO {

    private final BookingRepository bookingRepository;

    public BookingDAOImpl(
            BookingRepository bookingRepository) {

        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Optional<Booking> findByBookingRef(
            String bookingRef) {

        return bookingRepository.findByBookingRef(bookingRef);
    }
}