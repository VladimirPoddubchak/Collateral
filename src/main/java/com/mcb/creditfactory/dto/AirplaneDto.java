package com.mcb.creditfactory.dto;

/**
 * Created by @author Vladimir Poddubchak @date 28.08.2020.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName(value = "airplane")
@Builder
@Getter
public class AirplaneDto extends Collateral implements CollateralObject {
    private UUID id;
    private CollateralType collateralType;
    private String brand;
    private String model;
    private String manufacturer;
    private Short year;
    private int fuelCapacity;
    private int seats;
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
        return CollateralType.AIRPLANE;
    }
}