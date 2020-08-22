package com.mcb.creditfactory.service.collateral;

import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by @author Vladimir Poddubchak @date 19.08.2020.
 */

@AllArgsConstructor
public class CollateralObjectAdaptor  implements CollateralObject {
    @Autowired
    CommonCollateralService commonCollateralService;
    
    private Collateral dto;
    
    private CollateralServiceInterface service = commonCollateralService.getServiceMap().get(dto.getType());

    public CollateralObjectAdaptor(Collateral dto) {
    }

    @Override
    public BigDecimal getValue() {
        return service.getValue(dto);
    }

    @Override
    public Short getYear() {
        return service.getYear(dto);
    }

    @Override
    public LocalDate getDate() {
        return service.getDate(dto);
    }

    @Override
    public CollateralType getType() {
        return dto.getType();
    }
}
