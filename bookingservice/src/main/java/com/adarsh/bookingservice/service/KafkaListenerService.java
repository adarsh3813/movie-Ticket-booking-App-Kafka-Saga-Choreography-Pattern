package com.adarsh.bookingservice.service;

import com.adarsh.booking_app_common.common.KafkaConfiguration;
import com.adarsh.booking_app_common.events.SeatReservedEvent;
import com.adarsh.bookingservice.model.BookingDetails;
import com.adarsh.bookingservice.model.BookingStatus;
import com.adarsh.bookingservice.respository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class KafkaListenerService {

    private final BookingRepository bookingRepository;

    @KafkaListener(topics = KafkaConfiguration.SEAT_RESERVED_EVENT_TOPIC, groupId = KafkaConfiguration.MOVIE_BOOKING_GROUP)
    public void consumeSeatReservationEvent(SeatReservedEvent seatReservedEvent) {

        BookingDetails bookingDetails = bookingRepository.findById(seatReservedEvent.getBookingId())
                .orElse(null);

        if(Objects.isNull(bookingDetails)) return;

        if(seatReservedEvent.isSeatReserved()) {
            bookingDetails.setMessage("Booking is Successful");
            bookingDetails.setTotalAmount(seatReservedEvent.getTotalAmount());
            bookingDetails.setBookingStatus(BookingStatus.CONFIRMED);
        } else {
            bookingDetails.setMessage("Sorry, booking was unsuccessful");
            bookingDetails.setBookingStatus(BookingStatus.FAILED);
        }
        bookingRepository.save(bookingDetails);
    }

}
