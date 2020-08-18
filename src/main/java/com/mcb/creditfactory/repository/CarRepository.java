package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CarRepository extends CrudRepository<Car, UUID> {
}
