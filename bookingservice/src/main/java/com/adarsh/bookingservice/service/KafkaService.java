package com.adarsh.bookingservice.service;

import com.adarsh.booking_app_common.common.KafkaConfiguration;
import com.adarsh.booking_app_common.events.BookingCreatedEvent;
import com.adarsh.bookingservice.model.BookingDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendBookingTicketEvent(BookingDetails bookingDetails) {

        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent();
        bookingCreatedEvent.setBookingId(bookingDetails.getId());
        bookingCreatedEvent.setMovieCode(bookingDetails.getMovieCode());
        bookingCreatedEvent.setSeats(bookingDetails.getSeats());
        bookingCreatedEvent.setUserId(bookingDetails.getUserId());

        log.info("Sending Booking created request to Seat Inventory service: {}", bookingDetails.getId());
        CompletableFuture<SendResult<String, Object>> result = kafkaTemplate.send(KafkaConfiguration.MOVIE_BOOKING_EVENT_TOPIC, bookingCreatedEvent);

        result.whenComplete((res, ex) -> {
            if(Objects.isNull(ex)) {
                log.info("Message successfully sent to Seat Inventory service for booking id {}", bookingDetails.getId());
            } else {
                log.error("Encountered an error while sending Booking created event to Seat Inventory service: {} ", ex.getMessage());
            }
        });
    }

}
