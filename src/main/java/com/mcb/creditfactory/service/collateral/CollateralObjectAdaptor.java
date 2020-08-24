package com.mcb.creditfactory.service.collateral;

import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by @author Vladimir Poddubchak @date 19.08.2020.
 */

@Slf4j
public class CollateralObjectAdaptor  implements CollateralObject {
    @Autowired
    private CommonCollateralService commonCollateralService;
    
    private Collateral dto;

    public CollateralObjectAdaptor(Collateral dto) {
        this.dto = dto;
        log.info("Dto type: {}",dto.getType());
    }

    @Override
    public BigDecimal getValue() {
         CollateralServiceInterface service = commonCollateralService.getServiceMap().get(dto.getType());
         return service.getValue(dto);
    }

    @Override
    public Short getYear() {
        CollateralServiceInterface service = commonCollateralService.getServiceMap().get(dto.getType());
        return service.getYear(dto);
    }

    @Override
    public LocalDate getDate() {
        CollateralServiceInterface service = commonCollateralService.getServiceMap().get(dto.getType());
        return service.getDate(dto);
    }

    @Override
    public CollateralType getType() {
        return dto.getType();
    }
}
