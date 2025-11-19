package com.adarsh.bookingservice.dto;

import com.adarsh.bookingservice.model.BookingStatus;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookingStatusResponse {
    private UUID id;
    private BookingStatus bookingStatus;
    private Double totalAmount;
    private String message;
    private List<String> seats;
    private String userId;
}
