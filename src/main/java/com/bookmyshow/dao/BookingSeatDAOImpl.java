package com.bookmyshow.dao;

import com.bookmyshow.entity.BookingSeat;
import com.bookmyshow.repository.BookingSeatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingSeatDAOImpl implements BookingSeatDAO {

    private final BookingSeatRepository bookingSeatRepository;

    public BookingSeatDAOImpl(
            BookingSeatRepository bookingSeatRepository) {

        this.bookingSeatRepository = bookingSeatRepository;
    }

    @Override
    public BookingSeat save(BookingSeat bookingSeat) {
        return bookingSeatRepository.save(bookingSeat);
    }

    @Override
    public List<BookingSeat> findAll() {
        return bookingSeatRepository.findAll();
    }

    @Override
    public List<BookingSeat> findByBookingId(Long bookingId) {
        return bookingSeatRepository.findByBookingId(bookingId);
    }
}