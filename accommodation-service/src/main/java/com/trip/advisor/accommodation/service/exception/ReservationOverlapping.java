package com.trip.advisor.accommodation.service.exception;

public class ReservationOverlapping extends RuntimeException {


    public ReservationOverlapping(String message) {
        super(message);
    }
}
