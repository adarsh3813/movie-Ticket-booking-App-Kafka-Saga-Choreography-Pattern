package com.adarsh.seatinventory.listener;

import com.adarsh.booking_app_common.common.KafkaConfiguration;
import com.adarsh.booking_app_common.events.BookingCreatedEvent;
import com.adarsh.booking_app_common.events.PaymentEvent;
import com.adarsh.seatinventory.service.SeatInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatInventoryListener {

    private final SeatInventoryService service;

    @KafkaListener(topics = KafkaConfiguration.MOVIE_BOOKING_EVENT_TOPIC, groupId = KafkaConfiguration.SEAT_EVENT_GROUP)
    public void listenToBookingEvent(BookingCreatedEvent bookingCreatedEvent) {

        log.info("Message from booking service consumed by seat inventory service for booking id: {}", bookingCreatedEvent.getBookingId());
        service.handleBooking(bookingCreatedEvent);
    }

    @KafkaListener(topics = KafkaConfiguration.PAYMENT_EVENT_TOPIC, groupId = KafkaConfiguration.SEAT_EVENT_GROUP)
    public void listenPaymentEvent(PaymentEvent paymentEvent) {
        log.info("Message from Payment service consumed by seat inventory service for booking id: {} and payment status: {}", paymentEvent.getBookingId(), paymentEvent.isPaymentSuccessful());
        service.handlePayment(paymentEvent);
    }
}
