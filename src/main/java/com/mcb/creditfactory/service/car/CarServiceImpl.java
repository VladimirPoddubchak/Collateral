package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
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
    public Car fromDto(Collateral dto) {
        CarDto carDto = (CarDto) dto;
        return Car.builder()
                .id(carDto.getId())
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .power(carDto.getPower())
                .year(carDto.getYear())
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
    public boolean approve(CarDto dto) {
        return approveService.approve(dto)==0;
    }

    @Override
    @Transactional
    public UUID saveDto(Collateral dto) {
        Car savedCar = carRepository.save(this.fromDto(dto));
        AssessDto assessDto = ((CarDto)dto).getActualAssessDto();
        assessDto.setCollateralType(CollateralType.CAR);
        assessDto.setCollateralId(savedCar.getId());
        assessService.saveDto(assessDto);
        return this.getId(savedCar);
    }

    @Override
    public Optional<Car> load(UUID id) {
        return carRepository.findById(id);
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
    public BigDecimal getValue(Collateral dto) {
        return assessService.getActualAssessDto(((CarDto)dto).getId()).getValue();
    }

    @Override
    public Short getYear(Collateral dto) {
        return ((CarDto) dto).getYear();
    }

    @Override
    public LocalDate getDate(Collateral dto) {
        return assessService.getActualAssessDto(((CarDto)dto).getId()).getAssessDate().toLocalDate();
    }
}

