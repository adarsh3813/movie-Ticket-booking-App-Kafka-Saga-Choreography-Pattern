package com.adarsh.paymentservice.service;

import com.adarsh.booking_app_common.common.KafkaConfiguration;
import com.adarsh.booking_app_common.events.SeatReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentListeners {

    private final PaymentService paymentService;

    @KafkaListener(topics = KafkaConfiguration.SEAT_RESERVED_EVENT_TOPIC, groupId = KafkaConfiguration.PAYMENT_EVENT_GROUP)
    public void consumeSeatReservedEvent(SeatReservedEvent seatReservedEvent) {

        log.info("Consuming Seat reserved event for booking id: {} and reserved status: {}", seatReservedEvent.getBookingId(), seatReservedEvent.isSeatReserved());
        paymentService.handlePayment(seatReservedEvent);

    }

}
