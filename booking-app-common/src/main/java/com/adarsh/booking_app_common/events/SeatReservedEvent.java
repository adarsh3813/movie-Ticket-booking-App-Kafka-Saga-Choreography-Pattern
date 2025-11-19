package com.adarsh.booking_app_common.events;

import lombok.Data;

import java.util.UUID;

@Data
public class SeatReservedEvent {
    private boolean seatReserved;
    private Double totalAmount;
    private UUID bookingId;
}
