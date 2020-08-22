package com.mcb.creditfactory.service.assess;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.model.Assess;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 19.08.2020.
 */
@Component
public class AssessServiceImpl implements AssessService {
    @Override
    public AssessDto getActualAssessDto(UUID uuid) {
        return null;
    }

    @Override
    public List<AssessDto> getAssessDtoList(UUID uuid) {
        return null;
    }

    @Override
    public Assess fromDto(AssessDto dto) {
        return Assess.builder()
                .type(dto.getType())
                .value(dto.getValue())
                .assessDate(dto.getDate())
                .status(dto.isStatus())
                .build();
    }

    @Override
    public AssessDto toDto(Assess assess) {
        return null;
    }

    @Override
    public List<AssessDto> toAssessDtoList(List<Assess> assessList) {
        return null;
    }

    @Override
    public List<Assess> fromAssessDtoList(List<AssessDto> assessDtoList) {
        return null;
    }
}
