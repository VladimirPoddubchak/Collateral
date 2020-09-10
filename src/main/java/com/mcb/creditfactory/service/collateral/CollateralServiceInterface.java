package com.mcb.creditfactory.service.collateral;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.CollateralModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 18.08.2020.
 */

public interface CollateralServiceInterface<M extends CollateralModel, D extends Collateral> {

    M fromDto (D dto);
    D toDto (M object);

    D approve(D dto);

    UUID saveDto(D dto);

    M load (UUID id);

    UUID getId(M object);

    String getCode();

    D addAssess(AssessDto assessDto);

    List<AssessDto> assessList(D dto);

    @Autowired
    default void autoRegistration(CommonCollateralService service){
        service.registerService(this);
    }
}
