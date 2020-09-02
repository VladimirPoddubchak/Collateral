package com.mcb.creditfactory.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

@Service
@Slf4j
public class ExternalApproveService {

    private HashMap<String,ApproveService> serviceMap = new HashMap<>();

    public ApproveService getApproveService(String code) {
        return serviceMap.get(code);
    }

    public void registerService(ApproveService service) {
        serviceMap.put(service.getCode(),service);
        log.info("Collateral service: {} registered by key: {}",serviceMap.get(service.getCode()),service.getCode());
        log.info("Service map: {} ",serviceMap);
    }

//
//
//    private static final LocalDate MIN_ASSESS_DATE = LocalDate.of(2017, Month.OCTOBER, 1);
//    private static final int MIN_CAR_YEAR = 2000;
//    private static final BigDecimal MIN_CAR_VALUE = BigDecimal.valueOf(1000000);
//    private static final int MIN_PLANE_YEAR = 1991;
//    private static final BigDecimal MIN_PLANE_VALUE = BigDecimal.valueOf(230000000);


    public int approve(CollateralObject object) {
        if (object.getDate() == null ||object.getYear() == null || object.getValue() == null || object.getType() == null) {
            return -1;
        }
        ApproveService approveService = serviceMap.get(object.getType().toString());
        return approveService.approve(object);
    }
//
//    private int approveCar(CollateralObject object) {
//        if (object.getYear() < MIN_CAR_YEAR) {
//            return -10;
//        }
//        if (object.getDate().isBefore(MIN_ASSESS_DATE)) {
//            return -11;
//        }
//        if (object.getValue().compareTo(MIN_CAR_VALUE) < 0) {
//            return -12;
//        }
//
//        return 0;
//    }
//
//    private int approvePlane(CollateralObject object) {
//        if (object.getYear() < MIN_PLANE_YEAR) {
//            return -20;
//        }
//        if (object.getDate().isBefore(MIN_ASSESS_DATE)) {
//            return -21;
//        }
//        if (object.getValue().compareTo(MIN_PLANE_VALUE) < 0) {
//            return -22;
//        }
//
//        return 0;
//    }


}
