package com.adarsh.seatinventory.service;

import com.adarsh.booking_app_common.common.KafkaConfiguration;
import com.adarsh.booking_app_common.events.BookingCreatedEvent;
import com.adarsh.booking_app_common.events.PaymentEvent;
import com.adarsh.booking_app_common.events.SeatReservedEvent;
import com.adarsh.seatinventory.model.SeatInventory;
import com.adarsh.seatinventory.model.SeatStatus;
import com.adarsh.seatinventory.repository.SeatInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeatInventoryService {

    private final SeatInventoryRepository seatRepository;
    private final SeatReservedEventProducer seatReservedEventProducer;

    public void handleBooking(BookingCreatedEvent bookingCreatedEvent) {

        List<SeatInventory> seats = seatRepository.findByMovieCodeAndSeatNumberIn(bookingCreatedEvent.getMovieCode(), bookingCreatedEvent.getSeats());
        boolean allPresent = seats.stream()
                .allMatch(s -> s.getStatus().equals(SeatStatus.AVAILABLE));

        if(allPresent) {

            log.info("SeatService:: All seats were available proceeding to book seats");
            seats.forEach(seat -> {
                seat.setBookingId(bookingCreatedEvent.getBookingId());
                seat.setStatus(SeatStatus.LOCKED);
            });

            seatRepository.saveAll(seats);
            Double totalAmount = seats.stream()
                    .mapToDouble(SeatInventory::getPrice).sum();
            seatReservedEventProducer.produceSeatReservedEvent(bookingCreatedEvent.getBookingId(), totalAmount, true);

        } else {
            log.info("SeatService:: All seats were not available proceeding to send cancel booking event");
            seatReservedEventProducer.produceSeatReservedEvent(bookingCreatedEvent.getBookingId(), 0.0, false);
        }
    }

    public void handlePayment(PaymentEvent paymentEvent) {

        List<SeatInventory> seats = seatRepository.findByBookingId(paymentEvent.getBookingId());

        if(paymentEvent.isPaymentSuccessful()) {

            log.info("Payment was successful, marking seats as reserved");

            seats.forEach(seat -> seat.setStatus(SeatStatus.RESERVED));
            seatRepository.saveAll(seats);
            seatReservedEventProducer.produceSeatReservedEvent(paymentEvent.getBookingId(), paymentEvent.getTotalAmount(), true);
        } else {

            log.info("Payment was unsuccessful, marking seats as Available");

            seats.forEach(seat -> {
                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setBookingId(null);
            });
            seatRepository.saveAll(seats);
            seatReservedEventProducer.produceSeatReservedEvent(paymentEvent.getBookingId(), paymentEvent.getTotalAmount(), false);
        }

    }
}
