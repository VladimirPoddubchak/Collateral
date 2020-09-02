package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.service.collateral.CommonCollateralService;
import com.mcb.creditfactory.service.collateral.CollateralServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO: reimplement this
@Service
public class CollateralService {

    @Autowired
    CommonCollateralService collateralService;

    @SuppressWarnings("ConstantConditions")
    public UUID saveCollateral(Collateral dto) {

        if (!(dto instanceof Collateral)) {
            throw new IllegalArgumentException();
        }

        CollateralServiceInterface service = collateralService.getServiceMap().get(dto.getType().toString());
        service.approve(dto);

        return service.saveDto(dto);
    }

    public Collateral getInfo(Collateral dto) {
        if (!(dto instanceof Collateral)) {
            throw new IllegalArgumentException();
        }

        CollateralServiceInterface service = collateralService.getServiceMap().get(dto.getType().toString());


        return (Collateral) service.toDto(service.load(service.getId(service.fromDto(dto))));
    }

    public Collateral addAssess (AssessDto assessDto) {

        if (!(assessDto instanceof AssessDto)) {
            throw new IllegalArgumentException();
        }

        CollateralServiceInterface service = collateralService.getServiceMap().get(assessDto.getCollateralType().toString());

        return service.addAssess(assessDto);
    }

    public List<AssessDto> assessList(Collateral dto) {
        CollateralServiceInterface service = collateralService.getServiceMap().get(dto.getType().toString());

        return service.assessList(dto);
    }


//    @Autowired
//    private CarService carService;
//
    //    public UUID saveCollateral(Collateral object) {
//        if (!(object instanceof CarDto)) {
//            throw new IllegalArgumentException();
//        }
//
//        CarDto car = (CarDto) object;
//        boolean approved = carService.approve(car);
//        if (!approved) {
//            return null;
//        }
//
//        return Optional.of(car)
//                .map(carService::fromDto)
//                .map(carService::save)
//                .map(carService::getId)
//                .orElse(null);
//    }
//
//    public Collateral getInfo(Collateral object) {
//        if (!(object instanceof CarDto)) {
//            throw new IllegalArgumentException();
//        }
//
//        return Optional.of((CarDto) object)
//                .map(carService::fromDto)
//                .map(carService::getId)
//                .flatMap(carService::load)
//                .map(carService::toDTO)
//                .orElse(null);
//    }
}
