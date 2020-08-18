package com.mcb.creditfactory.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("car")
public class CarDto implements Collateral {
    private UUID id;
    private String brand;
    private String model;
    private Double power;
    private Short year;
//    private BigDecimal value;
}
