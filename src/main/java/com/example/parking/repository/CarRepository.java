package com.example.parking.repository;

import com.example.parking.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long>{
    List<Car> findByOwner_Id(Long ownerId);
}
