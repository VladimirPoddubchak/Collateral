package com.mcb.creditfactory.service.collateral;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.CollateralModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// TODO: reimplement this
@Service
public class CollateralService {

    @Autowired
    CommonCollateralService collateralService;

    @SuppressWarnings("ConstantConditions")
      public<M extends CollateralModel, D extends Collateral> UUID saveCollateral(D dto) {

        CollateralServiceInterface<M,D> service = (CollateralServiceInterface<M, D>) collateralService.getServiceMap().get(dto.getType().toString());
        service.approve(dto);
        return service.saveDto(dto);
    }

    public <M extends CollateralModel, D extends Collateral> Collateral getCollateralInfo(D dto) {

        CollateralServiceInterface<M,D> service = (CollateralServiceInterface<M, D>) collateralService.getServiceMap().get(dto.getType().toString());
        return (Collateral) service.toDto(service.load(service.getId(service.fromDto(dto))));
    }

    public <D extends Collateral> D addAssess (AssessDto assessDto) {

        CollateralServiceInterface<? extends CollateralModel,? extends Collateral> service = collateralService.getServiceMap().get(assessDto.getCollateralType().toString());
        return (D) service.addAssess(assessDto);
    }

    public <M extends CollateralModel, D extends Collateral> List<AssessDto> assessList(D  dto) {

        CollateralServiceInterface<M,D> service = (CollateralServiceInterface<M, D>) collateralService.getServiceMap().get(dto.getType().toString());
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
