package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.repository.CarRepository;
import com.mcb.creditfactory.service.assess.AssessService;
import com.mcb.creditfactory.service.collateral.CollateralServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 18.08.2020.
 */
@Slf4j
@Component
public class CarServiceImpl implements CollateralServiceInterface<Car, CarDto> {

    @Autowired
    CarRepository carRepository;

    @Autowired
    AssessService assessService;

    @Autowired
    ExternalApproveService approveService;

    @Override
    public Car fromDto(CarDto dto) {

        return Car.builder()
                .id(dto.getId())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .power(dto.getPower())
                .year(dto.getYear())
                .build();
    }

    @Override
    public CarDto toDto(Car object) {
        return new CarDto(
            object.getId(),
            object.getBrand(),
            object.getModel(),
            object.getPower(),
            object.getYear(),
            assessService.getActualAssessDto(object.getId())
        );
    }

    @Override
    public CarDto approve(CarDto dto) {
        boolean isApproved = approveService.approve(dto)==0;
        dto.getActualAssessDto().setStatus(isApproved);
        return dto;
    }

    @Override
    @Transactional
    public UUID saveDto(CarDto dto) {
        if((dto==null)||(dto.getBrand()==null)||(dto.getModel()==null)||(dto.getPower()==null)||(dto.getYear()==null)
                ||(dto.getActualAssessDto()==null)||(dto.getActualAssessDto().getAssessDate()==null)||(dto.getActualAssessDto().getValue()==null)){
            throw new IllegalArgumentException();
        }
        Car savedCar = carRepository.save(this.fromDto(dto));
        AssessDto assessDto = dto.getActualAssessDto();
        assessDto.setCollateralType(CollateralType.CAR);
        assessDto.setCollateralId(savedCar.getId());
        assessService.saveDto(assessDto);
        return this.getId(savedCar);
    }

    @Override
    public Car load(UUID id) {
        return carRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public UUID getId(Car object) {
        return object.getId();
    }

    @Override
    public String getCode() {
        return "CAR";
    }

    @Override
    @Transactional
    public CarDto addAssess(AssessDto assessDto) {
        if((assessDto==null)||(assessDto.getCollateralId()==null)||(assessDto.getCollateralType()==null)
                ||(assessDto.getValue()==null)||(assessDto.getAssessDate()==null)){
            throw new IllegalArgumentException();
        }

        CarDto carDto =  this.toDto(load(assessDto.getCollateralId()));
        carDto.setActualAssessDto(assessDto);
        carDto.setActualAssessDto(assessService.saveDto(approve(carDto).getActualAssessDto()));
        return carDto;
    }

    @Override
    public List<AssessDto> assessList(CarDto dto) {
        return assessService.getAssessDtoList(dto.getId());
    }
}

