package com.example.parking.controller;

import com.example.parking.model.ParkingSpot;
import com.example.parking.repository.ParkingSpotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
public class ParkingSpotController {

    private final ParkingSpotRepository repository;

    public ParkingSpotController(ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ParkingSpot> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ParkingSpot spot) {
        if (repository.existsBySpotNumber(spot.getSpotNumber())) {
            return ResponseEntity.badRequest().body("Место уже существует");
        }
        repository.save(spot);
        return ResponseEntity.ok("Место добавлено");
    }

    @PutMapping("/{id}/free")
    public ResponseEntity<String> freeSpot(@PathVariable Long id) {
        return repository.findById(id).map(spot -> {
            spot.setOccupied(false);
            repository.save(spot);
            return ResponseEntity.ok("Место освобождено");
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<String> reserveSpot(@PathVariable Long id) {
        return repository.findById(id).map(spot -> {
            spot.setOccupied(true);
            repository.save(spot);
            return ResponseEntity.ok("Место забронировано");
        }).orElse(ResponseEntity.notFound().build());
    }
}