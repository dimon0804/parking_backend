package com.example.parking.controller;

import com.example.parking.model.Booking;
import com.example.parking.repository.BookingRepository;
import com.example.parking.repository.CarRepository;
import com.example.parking.model.Car;
import com.example.parking.repository.ParkingSpotRepository;
import com.example.parking.model.ParkingSpot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")

public class BookingController {
    private final BookingRepository repository;
    private final CarRepository carRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    public BookingController(BookingRepository repository,
                             CarRepository carRepository,
                             ParkingSpotRepository parkingSpotRepository) {
        this.repository = repository;
        this.carRepository = carRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @GetMapping
    public List<Booking> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        Long carId = booking.getCar().getId();
        Long spotId = booking.getParkingSpot().getId();

        Optional<Car> car = carRepository.findById(carId);
        if (car.isEmpty()) {
            return ResponseEntity.badRequest().body("Машина не найдена");
        }

        Optional<ParkingSpot> spot = parkingSpotRepository.findById(spotId);
        if (spot.isEmpty()) {
            return ResponseEntity.badRequest().body("Парковочное место не найдено");
        }

        List<Booking> overlappingBookings = repository.findByParkingSpot_IdAndEndTimeAfterAndStartTimeBefore(
                spotId, booking.getStartTime(), booking.getEndTime());
        if (!overlappingBookings.isEmpty()) {
            return ResponseEntity.badRequest().body("Парковочное место занято в этот период");
        }

        booking.setCar(car.get());
        booking.setParkingSpot(spot.get());

        repository.save(booking);
        return ResponseEntity.ok("Бронирование создано");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        Optional<Booking> bookingOpt = repository.findById(id);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Бронирование удалено");
    }
}
