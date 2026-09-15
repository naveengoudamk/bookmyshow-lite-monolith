package com.bookmyshow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 400);
        response.put("data", null);
        response.put("error_message", errors);

        return response;
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleMovieNotFound(
            MovieNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 404);
        response.put("data", null);
        response.put("error_message", ex.getMessage());

        return response;
    }

    @ExceptionHandler(TheatreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleTheatreNotFound(
            TheatreNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 404);
        response.put("data", null);
        response.put("error_message", ex.getMessage());

        return response;
    }

    @ExceptionHandler(ScreenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> handleScreenNotFound(ScreenNotFoundException ex){

        Map<String,Object> response=new HashMap<>();

        response.put("statusCode",404);
        response.put("data",null);
        response.put("error_message",ex.getMessage());

        return response;
    }

    @ExceptionHandler(ScreenSeatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleScreenSeatNotFound(
            ScreenSeatNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 404);
        response.put("data", null);
        response.put("error_message", ex.getMessage());

        return response;
    }

    @ExceptionHandler(ShowNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleShowNotFound(
            ShowNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 404);
        response.put("data", null);
        response.put("error_message", ex.getMessage());

        return response;
    }

    @ExceptionHandler(ShowSeatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleShowSeatNotFound(
            ShowSeatNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 404);
        response.put("data", null);
        response.put("error_message", ex.getMessage());

        return response;
    }

    @ExceptionHandler(SeatNotAvailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleSeatNotAvailable(
            SeatNotAvailableException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 409);
        response.put("data", null);
        response.put("error_message", ex.getMessage());

        return response;
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleBookingNotFound(
            BookingNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("statusCode", 404);
        response.put("data", null);
        response.put("error_message", ex.getMessage());

        return response;
    }
}