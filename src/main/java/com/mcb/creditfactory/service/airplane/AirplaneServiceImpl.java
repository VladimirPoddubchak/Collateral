package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.repository.AirplaneRepository;
import com.mcb.creditfactory.service.assess.AssessService;
import com.mcb.creditfactory.service.collateral.CollateralServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 28.08.2020.
 */

@Slf4j
@Component
public class AirplaneServiceImpl implements CollateralServiceInterface<Airplane, AirplaneDto> {

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    AssessService assessService;

    @Autowired
    ExternalApproveService approveService;

    @Override
    public Airplane fromDto(AirplaneDto dto) {
        return  Airplane.builder()
                .id(dto.getId())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .manufacturer(dto.getManufacturer())
                .year(dto.getYear())
                .fuelCapacity(dto.getFuelCapacity())
                .seats(dto.getSeats())
                .build();
    }

    @Override
    public AirplaneDto toDto(Airplane object) {
        return new AirplaneDto(
                object.getId(),
                CollateralType.AIRPLANE,
                object.getBrand(),
                object.getModel(),
                object.getManufacturer(),
                object.getYear(),
                object.getFuelCapacity(),
                object.getSeats(),
                assessService.getActualAssessDto(object.getId()));
    }

    @Override
    public AirplaneDto approve(AirplaneDto dto) {
        boolean isApproved = approveService.approve(dto)==0;
        dto.getActualAssessDto().setStatus(isApproved);
        return dto;
    }

    @Override
    @Transactional
    public UUID saveDto(AirplaneDto dto) {
        Airplane savedAirplane = airplaneRepository.save(this.fromDto(dto));
        AssessDto assessDto = (dto).getActualAssessDto();
        assessDto.setCollateralType(CollateralType.AIRPLANE);
        assessDto.setCollateralId(savedAirplane.getId());
        assessService.saveDto(assessDto);
        return this.getId(savedAirplane);
    }

    @Override
    public Airplane load(UUID id) {
        return airplaneRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public UUID getId(Airplane object) {
        return object.getId();
    }

    @Override
    public String getCode() {
        return "AIRPLANE";
    }

    @Override
    public AirplaneDto addAssess(AssessDto assessDto) {
        if((assessDto==null)||(assessDto.getCollateralId()==null)||(assessDto.getCollateralType()==null)
                ||(assessDto.getValue()==null)||(assessDto.getAssessDate()==null)){
            throw new IllegalArgumentException();
        }

        AirplaneDto airplaneDto =  this.toDto(load(assessDto.getCollateralId()));
        airplaneDto.setActualAssessDto(assessDto);
        airplaneDto.setActualAssessDto(assessService.saveDto(approve(airplaneDto).getActualAssessDto()));
        return airplaneDto;
    }

    @Override
    public List<AssessDto> assessList(AirplaneDto dto) {
        return assessService.getAssessDtoList(dto.getId());
    }
}
