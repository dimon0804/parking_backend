package com.example.parking.repository;

import com.example.parking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByParkingSpot_IdAndEndTimeAfterAndStartTimeBefore(
            Long parkingSpotId, LocalDateTime startTime, LocalDateTime endTime
    );
}
