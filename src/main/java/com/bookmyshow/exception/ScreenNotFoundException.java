package com.bookmyshow.exception;

public class ScreenNotFoundException extends RuntimeException{

    public ScreenNotFoundException(Long id){
        super("Screen not found with id: "+id);
    }
}