package com.bookmyshow.exception;

public class ShowSeatNotFoundException extends RuntimeException {

    public ShowSeatNotFoundException(Long id) {
        super("Show seat not found with id: " + id);
    }
}