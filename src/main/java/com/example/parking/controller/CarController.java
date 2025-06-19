package com.example.parking.controller;

import com.example.parking.dto.CreateCarRequest;
import com.example.parking.model.Car;
import com.example.parking.model.CarOwner;
import com.example.parking.repository.CarRepository;
import com.example.parking.repository.CarOwnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository repository;
    private final CarOwnerRepository carOwnerRepository;

    public CarController(CarRepository repository, CarOwnerRepository carOwnerRepository) {
        this.repository = repository;
        this.carOwnerRepository = carOwnerRepository;
    }

    @GetMapping
    public List<Car> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createCar(@RequestBody CreateCarRequest request) {
        Optional<CarOwner> owner = carOwnerRepository.findById(request.getOwnerId());
        if (owner.isEmpty()) {
            return ResponseEntity.badRequest().body("Владелец не найден");
        }

        Car car = new Car();
        car.setLicensePlate(request.getLicensePlate());
        car.setModel(request.getModel());
        car.setOwner(owner.get());

        repository.save(car);
        return ResponseEntity.ok("Создана машина: " + car.getLicensePlate());
    }

    @GetMapping("/owner/{ownerId}")
    public List<Car> getAllByOwner(@PathVariable Long ownerId) {
        return repository.findByOwner_Id((ownerId));
    }
}
