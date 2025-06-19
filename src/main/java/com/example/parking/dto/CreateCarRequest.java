package com.example.parking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCarRequest {
    private String licensePlate;
    private String model;
    private Long ownerId;
}
