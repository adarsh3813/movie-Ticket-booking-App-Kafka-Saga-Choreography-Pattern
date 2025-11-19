package com.adarsh.seatinventory.repository;

import com.adarsh.seatinventory.model.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Integer> {
    List<SeatInventory> findByMovieCodeAndSeatNumberIn(String movieCode, List<String> seatNumbers);

    List<SeatInventory> findByBookingId(UUID bookingId);
}
