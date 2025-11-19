package com.adarsh.booking_app_common.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TicketBookingResponse {

    private UUID bookingId;
    private Double totalAmount;
    private List<String> seats;
    private String movieCode;

}
