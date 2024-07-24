package com.trip.advisor.events.service.constants;

public class EventMessage {

    private EventMessage(){}

    public static final String EVENT_NAME_SIZE = "Event name must be between 1 and 30 characters!";
    public static final String EVENT_NAME_REQUIRED = "Event name is required!";
    public static final String CITY_NAME_SIZE = "City name must be between 1 and 30 characters!";
    public static final String CITY_NAME_REQUIRED = "City name is required!";
    public static final String EVENT_TYPE_REQUIRED = "Event type is required!";
    public static final String EVENT_DATE_FUTURE = "Event date must be not in past!";
    public static final String EVENT_PRICE_POSITIVE = "Price must be positive!";
}
