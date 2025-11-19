package com.adarsh.bookingservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String userId;
    private String movieCode;
    private Double totalAmount;

    @ElementCollection
    private List<String> seats;

    @Enumerated
    private BookingStatus bookingStatus;

    private String message;

    @CreationTimestamp
    private LocalDate bookingTime;
    @UpdateTimestamp
    private LocalDate lastUpdated;

}
