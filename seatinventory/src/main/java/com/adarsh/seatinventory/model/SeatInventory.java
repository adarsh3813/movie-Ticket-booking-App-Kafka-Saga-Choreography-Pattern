package com.adarsh.seatinventory.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class SeatInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    private String seatNumber;
    private String screenCode;
    private String movieCode;
    private Integer price;
    private UUID bookingId;

    @UpdateTimestamp
    private LocalDate lastUpdated;
}
