package com.mcb.creditfactory.service.collateral;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
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

public interface CollateralServiceInterface<T, D> {

    T fromDto (Collateral dto);
    D toDto (T object);

    boolean approve(Collateral dto);

    UUID saveDto(D dto);

    T load (UUID id);

    UUID getId(T object);

    String getCode();

    @Autowired
    default void autoRegistration(CommonCollateralService service){
        service.registerService(this);
    }

    BigDecimal getValue(Collateral dto);

    Short getYear(Collateral dto);

    LocalDate getDate(Collateral dto);

    Collateral addAssess(AssessDto assessDto);

    List<AssessDto> assessList(Collateral dto);
}
