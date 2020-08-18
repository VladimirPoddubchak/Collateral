package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.model.Car;

import java.util.Optional;
import java.util.UUID;

public interface CarService {
    boolean approve(CarDto dto);
    Car save(Car car);
    Optional<Car> load(UUID id);
    Car fromDto(CarDto dto);
    CarDto toDTO(Car car);
    UUID getId(Car car);
}
