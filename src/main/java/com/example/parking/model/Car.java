package com.example.parking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String LicensePlate;
    private String model;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private CarOwner owner;

    public String getLicensePlate() {
        return LicensePlate;
    }
}
