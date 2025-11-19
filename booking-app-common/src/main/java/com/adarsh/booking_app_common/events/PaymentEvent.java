package com.adarsh.booking_app_common.events;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentEvent {
    private boolean paymentSuccessful;
    private UUID bookingId;
    private Double totalAmount;
}
