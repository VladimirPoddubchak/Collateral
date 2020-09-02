package com.mcb.creditfactory.service.assess;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.model.Assess;

import java.util.List;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 18.08.2020.
 */

public interface AssessService {
    AssessDto getActualAssessDto(UUID uuid);
    List<AssessDto> getAssessDtoList(UUID uuid);
    Assess fromDto (AssessDto dto);
    AssessDto toDto(Assess assess);
    Assess save (Assess assess);
    Assess saveDto (AssessDto dto);
    Collateral addAssess (AssessDto dto);
}
