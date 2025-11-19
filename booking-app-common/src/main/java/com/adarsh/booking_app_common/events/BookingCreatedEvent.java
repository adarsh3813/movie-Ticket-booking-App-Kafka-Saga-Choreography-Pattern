package com.adarsh.booking_app_common.events;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookingCreatedEvent {
    private UUID bookingId;
    private String userId;
    private String movieCode;
    private List<String> seats;
}
