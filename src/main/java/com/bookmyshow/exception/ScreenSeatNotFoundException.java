package com.bookmyshow.exception;

public class ScreenSeatNotFoundException extends RuntimeException {

    public ScreenSeatNotFoundException(Long id) {
        super("Screen seat not found with id: " + id);
    }
}