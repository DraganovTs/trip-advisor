package com.trip.advisor.events.service.exception;

public class EventAlreadyExistException extends RuntimeException{

    public EventAlreadyExistException(String message) {
        super(message);
    }
}
