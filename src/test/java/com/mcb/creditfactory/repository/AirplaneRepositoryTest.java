package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.Assess;
import com.mcb.creditfactory.model.CollateralType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AirplaneRepositoryTest {
        @Autowired
        AirplaneRepository repository;
    @Test
    void findAllAirplanes(){
        System.out.println(repository.findAll());
    }

    @Test
    void addAirplaneAssess(){
        System.out.println(repository.findAll());
        Assess assess = Assess.builder()
                .collateralId(UUID.fromString("010fd95e-117b-4a73-820e-7db546920afc"))
                .type(CollateralType.AIRPLANE)
                .assessDate(LocalDateTime.of(2019,01,01,0,0,0))
                .value(BigDecimal.valueOf( 250000000))
                .status(true)
                .build();

        Airplane airplane= repository.findById(UUID.fromString("010fd95e-117b-4a73-820e-7db546920afc")).get();
        airplane.getAssessList().add(assess);
        repository.save(airplane);
        System.out.println(repository.findById(UUID.fromString("010fd95e-117b-4a73-820e-7db546920afc")).get());
    }

}