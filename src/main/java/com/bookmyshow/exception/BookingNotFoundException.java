package com.bookmyshow.exception;

public class BookingNotFoundException
        extends RuntimeException {

    public BookingNotFoundException(String bookingRef) {
        super("Booking not found with reference: "
                + bookingRef);
    }
}