package com.adarsh.bookingservice.controller;

import com.adarsh.booking_app_common.dto.BookTicketRequestDto;
import com.adarsh.booking_app_common.dto.TicketBookingResponse;
import com.adarsh.bookingservice.dto.BookingStatusResponse;
import com.adarsh.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookTicketController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Object> bookTicker(@RequestBody BookTicketRequestDto dto) {
        TicketBookingResponse response = bookingService.bookTicket(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getStatus/{bookingId}")
    public ResponseEntity<Object> getBookingStatus(@PathVariable String bookingId) {
        try {
            BookingStatusResponse response = bookingService.getBookingStatus(bookingId);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
