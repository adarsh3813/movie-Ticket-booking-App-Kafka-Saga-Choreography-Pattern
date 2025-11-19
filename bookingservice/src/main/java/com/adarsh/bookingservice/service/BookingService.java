package com.adarsh.bookingservice.service;

import com.adarsh.booking_app_common.dto.BookTicketRequestDto;
import com.adarsh.booking_app_common.dto.TicketBookingResponse;
import com.adarsh.bookingservice.dto.BookingStatusResponse;
import com.adarsh.bookingservice.model.BookingDetails;
import com.adarsh.bookingservice.model.BookingStatus;
import com.adarsh.bookingservice.respository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final KafkaService kafkaService;

    public TicketBookingResponse bookTicket(BookTicketRequestDto dto) {

        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setSeats(dto.getSeats());
        bookingDetails.setMovieCode(dto.getMovieCode());
        bookingDetails.setUserId(dto.getUserId());
        bookingDetails.setBookingStatus(BookingStatus.PENDING);
        bookingDetails.setMessage("Booking is under progress, please wait");

        BookingDetails savedBooking = bookingRepository.save(bookingDetails);
        kafkaService.sendBookingTicketEvent(savedBooking);

        TicketBookingResponse response = new TicketBookingResponse();
        response.setBookingId(bookingDetails.getId());
        response.setMovieCode(bookingDetails.getMovieCode());
        response.setSeats(bookingDetails.getSeats());
        response.setTotalAmount(0.0);
        return response;
    }

    public BookingStatusResponse getBookingStatus(String bookingId) throws RuntimeException{
        BookingDetails bookingDetails = bookingRepository.findById(UUID.fromString(bookingId)).orElseThrow(() ->
                new RuntimeException("Invalid booking Id " + bookingId)
        );
        BookingStatusResponse response = new BookingStatusResponse();
        response.setBookingStatus(bookingDetails.getBookingStatus());
        response.setMessage(bookingDetails.getMessage());
        response.setSeats(bookingDetails.getSeats());
        response.setId(bookingDetails.getId());
        response.setUserId(bookingDetails.getUserId());
        response.setTotalAmount(bookingDetails.getTotalAmount());

        return response;
    }
}
