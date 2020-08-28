package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.model.Assess;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.service.car.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class AirplaneRepositoryTest {
    @Autowired
    CarRepository repository;

    @Autowired
    CarServiceImpl service;

    @Test
    void findAllAirplanes(){
        System.out.println(repository.findAll());
    }

    @Test
    void addCarAssess(){
        System.out.println(repository.findAll());
        Assess assess = Assess.builder()
                .collateralId(UUID.fromString("db9e2981-3042-4a27-bea0-194d1eb5adc0"))
                .type(CollateralType.CAR)
                .assessDate(LocalDateTime.of(2020,01,01,0,0,0))
                .value(BigDecimal.valueOf( 150000000))
                .status(true)
                .build();

        Car car= repository.findById(UUID.fromString("db9e2981-3042-4a27-bea0-194d1eb5adc0")).get();
//        car.getAssessList().add(assess);
        repository.save(car);
        System.out.println(repository.findById(UUID.fromString("db9e2981-3042-4a27-bea0-194d1eb5adc0")).get());
    }
    @Test
    void addCarFromCarDto(){
        System.out.println(repository.findAll());
        AssessDto assessDto = AssessDto.builder()
                .collateralType(CollateralType.CAR)
                .assessDate(LocalDateTime.of(2020,01,01,0,0,0))
                .value(BigDecimal.valueOf( 2500000))
                .build();
        System.out.println(assessDto);
        CarDto carDto= CarDto.builder()
                .brand("Subaru")
                .model("Forester")
                .power(185.)
                .year((short) 2018)
                .actualAssessDto(assessDto)
                .build();
        System.out.println(carDto);

        Car car= service.fromDto(carDto);
        System.out.println(car);
        service.saveDto(carDto);
        System.out.println(repository.findAll());
    }

}