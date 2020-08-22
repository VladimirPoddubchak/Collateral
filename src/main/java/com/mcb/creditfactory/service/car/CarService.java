package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Assess;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.repository.CarRepository;
import com.mcb.creditfactory.service.assess.AssessService;
import com.mcb.creditfactory.service.collateral.CollateralObjectAdaptor;
import com.mcb.creditfactory.service.collateral.CollateralServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 18.08.2020.
 */
@Slf4j
@Component
public class CarService implements CollateralServiceInterface<Car, CarDto> {

    @Autowired
    CarRepository carRepository;

    @Autowired
    AssessService assessService;

    @Autowired
    ExternalApproveService approveService;


    @Override
    public Car fromDto(Collateral dto) {
        CarDto carDto = (CarDto) dto;
        List<Assess> assessList = new ArrayList<>();
        assessList.add(assessService.fromDto(carDto.getActualAssess()));
        return Car.builder()
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .power(carDto.getPower())
                .year(carDto.getYear())
                .assessList(assessList)
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
        return approveService.approve(new CollateralObjectAdaptor(dto))==0;
    }

    @Override
    @Transactional
    public Car save(Car object) {
        List<Assess> tmpList = object.getAssessList();
        object.setAssessList(null);

        Car savedCar = carRepository.save(object);

        for (Assess assess:tmpList) {
            assess.setCollateralId(savedCar.getId());
        }

        savedCar.setAssessList(tmpList);
        return carRepository.save(savedCar);
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
    public BigDecimal getValue(Collateral object) {
        return assessService.getActualAssessDto(((CarDto)object).getId()).getValue();
    }

    @Override
    public Short getYear(Collateral object) {
        return ((CarDto) object).getYear();
    }

    @Override
    public LocalDate getDate(Collateral object) {
        return LocalDate.now();
    }
}

