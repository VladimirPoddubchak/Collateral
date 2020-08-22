package com.mcb.creditfactory.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mcb.creditfactory.external.CollateralType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonTypeName("car")
public class CarDto implements Collateral {
    private UUID id;
    private String brand;
    private String model;
    private Double power;
    private Short year;
    private AssessDto actualAssess;

    @Override
    @JsonIgnore
    public CollateralType getType() {
        return CollateralType.CAR;
    }


}
