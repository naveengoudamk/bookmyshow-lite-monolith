package com.bookmyshow.repository;

import com.bookmyshow.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingRef(String bookingRef);
}