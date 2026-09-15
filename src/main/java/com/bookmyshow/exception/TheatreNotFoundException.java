package com.bookmyshow.exception;

public class TheatreNotFoundException extends RuntimeException {

    public TheatreNotFoundException(Long id) {
        super("Theatre not found with id: " + id);
    }
}