package com.adarsh.paymentservice.service;

import com.adarsh.booking_app_common.common.KafkaConfiguration;
import com.adarsh.booking_app_common.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void generatePaymentEvent(UUID bookingId, Double totalAmount, boolean isPaymentDone) {

        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setPaymentSuccessful(isPaymentDone);
        paymentEvent.setTotalAmount(totalAmount);
        paymentEvent.setBookingId(bookingId);

        log.info("Creating Payment event for booking id: {} with status: {}", bookingId, isPaymentDone);

        kafkaTemplate.send(KafkaConfiguration.PAYMENT_EVENT_TOPIC, paymentEvent);

    }

}
