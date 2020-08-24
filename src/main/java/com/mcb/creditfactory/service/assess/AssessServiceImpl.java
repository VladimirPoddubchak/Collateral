package com.mcb.creditfactory.service.assess;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.model.Assess;
import com.mcb.creditfactory.repository.AssessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by @author Vladimir Poddubchak @date 19.08.2020.
 */
@Component
public class AssessServiceImpl implements AssessService {

    @Autowired
    AssessRepository assessRepository;

    @Override
    public AssessDto getActualAssessDto(UUID uuid) {
        return this.getAssessDtoList(uuid).get(0);
    }

    @Override
    public List<AssessDto> getAssessDtoList(UUID uuid) {

        return assessRepository.findAllByCollateralId(uuid)
                .stream()
                .map(this::toDto).sorted(Comparator.comparing(AssessDto::getDate))
                .collect(Collectors.toList());
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

        return assessList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Assess> fromAssessDtoList(List<AssessDto> assessDtoList) {
        return assessDtoList.stream()
                .map(this::fromDto)
                .collect(Collectors.toList());
    }
}
