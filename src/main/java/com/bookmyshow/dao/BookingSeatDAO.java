package com.bookmyshow.dao;

import com.bookmyshow.entity.BookingSeat;

import java.util.List;

public interface BookingSeatDAO {

    BookingSeat save(BookingSeat bookingSeat);

    List<BookingSeat> findAll();

    List<BookingSeat> findByBookingId(Long bookingId);
}