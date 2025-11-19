package com.adarsh.paymentservice.service;

import com.adarsh.booking_app_common.events.SeatReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private static final Double WALLET_BALANCE = 450.0;
    private final PaymentEventProducer paymentEventProducer;

    public void handlePayment(SeatReservedEvent seatReservedEvent) {

        log.info("Starting to handle payment for booking id: {} and total Amount: {}", seatReservedEvent.getBookingId(), seatReservedEvent.getTotalAmount());

        if(seatReservedEvent.getTotalAmount() > WALLET_BALANCE) {
            log.info("Insufficient balance, payment failed, creating event with status as failed for booking id: {}", seatReservedEvent.getBookingId());
            paymentEventProducer.generatePaymentEvent(seatReservedEvent.getBookingId(), seatReservedEvent.getTotalAmount(), false);
            return;
        }

        paymentEventProducer.generatePaymentEvent(seatReservedEvent.getBookingId(), seatReservedEvent.getTotalAmount(), true);
    }
}
