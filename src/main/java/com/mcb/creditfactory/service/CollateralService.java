package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.service.collateral.CommonCollateralService;
import com.mcb.creditfactory.service.collateral.CollateralServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        boolean approved = service.approve(dto);
        if (!approved) {
            return null;
        }

        return service.saveDto(dto);
    }

    public Collateral getInfo(Collateral dto) {
        if (!(dto instanceof Collateral)) {
            throw new IllegalArgumentException();
        }

        CollateralServiceInterface service = collateralService.getServiceMap().get(dto.getType().toString());


        return (Collateral) Optional.of(dto)
                .map(service::fromDto)
                .map(service::getId)
                .flatMap(service::load)
                .map(service::toDto)
                .orElse(null);

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
