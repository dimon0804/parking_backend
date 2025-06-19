package com.example.parking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parking_spots")
@Getter
@Setter
@NoArgsConstructor
public  class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spot_number", nullable = false, unique = true)
    private String spotNumber;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied = false;

    @Column(name = "price_per_hour")
    private Double pricePerHour;
}
