package com.mcb.creditfactory.service.assess;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.model.Assess;
import com.mcb.creditfactory.repository.AssessRepository;
import com.mcb.creditfactory.service.collateral.CollateralServiceInterface;
import com.mcb.creditfactory.service.collateral.CommonCollateralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by @author Vladimir Poddubchak @date 19.08.2020.
 */
@Component
@Slf4j
public class AssessServiceImpl implements AssessService {

    @Autowired
    AssessRepository assessRepository;

    @Autowired
    CommonCollateralService commonCollateralService;

    @Override
    public AssessDto getActualAssessDto(UUID uuid) {
        if (uuid==null){
            throw new IllegalArgumentException();
        }
        try{
            Assess result = assessRepository.findActualAssessByCollateralId(uuid);
            System.out.println(result);
            if (result== null) {
                throw new NoSuchElementException();
            }
            return this.toDto(result);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new DataAccessResourceFailureException("",ex);
        }
    }

    @Override
    public List<AssessDto> getAssessDtoList(UUID uuid) {
        if (uuid==null){
            throw new IllegalArgumentException();
        }
        try{
            List<AssessDto> result = this.assessRepository.findAllByCollateralId(uuid)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            if (result.isEmpty()) {
                throw new NoSuchElementException();
            }
            return result;

        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new DataAccessResourceFailureException("",ex);
        }
    }

    @Override
    public Assess fromDto(AssessDto dto) {
        return Assess.builder()
                .collateralId(dto.getCollateralId())
                .type(dto.getCollateralType())
                .value(dto.getValue())
                .assessDate(dto.getAssessDate())
                .status(dto.isStatus())
                .build();
    }

    @Override
    public AssessDto toDto(Assess assess) {
        return AssessDto.builder()
                .id(assess.getId())
                .collateralType(assess.getType())
                .collateralId(assess.getCollateralId())
                .value(assess.getValue())
                .assessDate(assess.getAssessDate())
                .status(assess.getStatus())
                .build();
    }

    @Override
    public Assess save(Assess assess) {
        return assessRepository.save(assess);
    }

    @Override
    public AssessDto saveDto(AssessDto dto) {
        return this.toDto(assessRepository.save(this.fromDto(dto)));
    }

    @Override
    public <D extends Collateral> D addAssess(AssessDto dto) {
        CollateralServiceInterface service = commonCollateralService.getServiceMap().get(dto.getCollateralType().toString());
        this.saveDto(dto);
        return (D) service.toDto(service.load(dto.getCollateralId()));
    }

}
