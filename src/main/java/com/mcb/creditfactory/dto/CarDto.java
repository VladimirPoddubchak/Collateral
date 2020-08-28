package com.mcb.creditfactory.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonTypeName("car")
public class CarDto implements Collateral, CollateralObject {
    private UUID id;
    private String brand;
    private String model;
    private Double power;
    private Short year;
    private AssessDto actualAssessDto;

    @Override
    @JsonIgnore
    public BigDecimal getValue() {
        return actualAssessDto.getValue();
    }

    @Override
    @JsonIgnore
    public LocalDate getDate() {
        return actualAssessDto.getAssessDate().toLocalDate();
    }

    @Override
    @JsonIgnore
    public CollateralType getType() {
        return CollateralType.CAR;
    }


}
