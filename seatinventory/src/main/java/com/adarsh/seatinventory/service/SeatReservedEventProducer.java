package com.adarsh.seatinventory.service;

import com.adarsh.booking_app_common.common.KafkaConfiguration;
import com.adarsh.booking_app_common.events.SeatReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatReservedEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void produceSeatReservedEvent(UUID bookingId, Double totalAmount, boolean isSeatReserved) {
        SeatReservedEvent seatReservedEvent = new SeatReservedEvent();
        seatReservedEvent.setBookingId(bookingId);
        seatReservedEvent.setTotalAmount(totalAmount);
        seatReservedEvent.setSeatReserved(isSeatReserved);

        log.info("Sending event for seat reservation with booking id: {} and status: {}", bookingId, isSeatReserved);

        kafkaTemplate.send(KafkaConfiguration.SEAT_RESERVED_EVENT_TOPIC, seatReservedEvent);
    }

}
