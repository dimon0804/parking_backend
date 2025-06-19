package com.example.parking.controller;

import com.example.parking.model.CarOwner;
import com.example.parking.repository.CarOwnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")

public class CarOwnerController {
    private final CarOwnerRepository repository;

    public CarOwnerController(CarOwnerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CarOwner> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createOwner(@RequestBody CarOwner owner) {
        repository.save(owner);
        return ResponseEntity.ok("Создан: " + owner.getFullName());

    }
}
