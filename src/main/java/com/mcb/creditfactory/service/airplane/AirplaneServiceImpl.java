package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.repository.AirplaneRepository;
import com.mcb.creditfactory.repository.CarRepository;
import com.mcb.creditfactory.service.assess.AssessService;
import com.mcb.creditfactory.service.collateral.CollateralServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public Airplane fromDto(Collateral dto) {
        AirplaneDto airplaneDto = (AirplaneDto) dto;
        return  Airplane.builder()
                .id(airplaneDto.getId())
                .brand(airplaneDto.getBrand())
                .model(airplaneDto.getModel())
                .manufacturer(airplaneDto.getManufacturer())
                .year(airplaneDto.getYear())
                .fuelCapacity(airplaneDto.getFuelCapacity())
                .seats(airplaneDto.getSeats())
                .build();


//        return  new Airplane(
//                airplaneDto.getId(),
//                airplaneDto.getBrand(),
//                airplaneDto.getModel(),
//                airplaneDto.getManufacturer(),
//                airplaneDto.getYear(),
//                airplaneDto.getFuelCapacity(),
//                airplaneDto.getSeats());
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
    public boolean approve(Collateral dto) {
        boolean isApproved = approveService.approve((CollateralObject) dto)==0;
        ((AirplaneDto) dto).getActualAssessDto().setStatus(isApproved);
        return isApproved;
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
    public BigDecimal getValue(Collateral dto) {
        return assessService.getActualAssessDto(((AirplaneDto)dto).getId()).getValue();
    }

    @Override
    public Short getYear(Collateral dto) {
        return ((AirplaneDto) dto).getYear();
    }

    @Override
    public LocalDate getDate(Collateral dto) {
        return assessService.getActualAssessDto(((AirplaneDto)dto).getId()).getAssessDate().toLocalDate();
    }

    @Override
    public Collateral addAssess(AssessDto assessDto) {
        return null;
    }

    @Override
    public List<AssessDto> assessList(Collateral dto) {
        AirplaneDto airplaneDto = (AirplaneDto  ) dto;
        return assessService.getAssessDtoList(airplaneDto.getId());
    }
}
