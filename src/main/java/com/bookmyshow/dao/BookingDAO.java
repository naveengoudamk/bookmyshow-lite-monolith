package com.bookmyshow.dao;

import com.bookmyshow.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingDAO {

    Booking save(Booking booking);

    List<Booking> findAll();

    Optional<Booking> findById(Long id);

    Optional<Booking> findByBookingRef(String bookingRef);
}