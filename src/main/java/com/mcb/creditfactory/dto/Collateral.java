package com.mcb.creditfactory.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mcb.creditfactory.external.CollateralType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CarDto.class)
})
public interface Collateral {

    CollateralType getType();
}
