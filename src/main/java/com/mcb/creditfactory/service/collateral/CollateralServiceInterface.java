package com.mcb.creditfactory.service.collateral;

import com.mcb.creditfactory.dto.Collateral;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 18.08.2020.
 */

public interface CollateralServiceInterface<T, D> {

    T fromDto (Collateral dto);
    D toDto (T object);

    boolean approve(D dto);
    T save (T object);
    Optional<T> load (UUID id);

    UUID getId(T object);
    String getCode();

    @Autowired
    default void autoRegistration(CommonCollateralService service){
        service.registerService(this);
    }

    BigDecimal getValue(Collateral object);

    Short getYear(Collateral object);

    LocalDate getDate(Collateral object);
}
