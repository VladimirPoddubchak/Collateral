package com.mcb.creditfactory.service.collateral;

import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.CollateralModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by @author Vladimir Poddubchak @date 18.08.2020.
 */

@Service
@Slf4j
public class CommonCollateralService {

    private Map<String, CollateralServiceInterface<? extends CollateralModel,? extends Collateral>> serviceMap = new ConcurrentHashMap<>();

    public void registerService(CollateralServiceInterface<? extends CollateralModel,? extends Collateral> service) {
        serviceMap.put(service.getCode(),service);
        log.info("Collateral service: {} registered by key: {}",serviceMap.get(service.getCode()),service.getCode());
        log.info("Service map: {} ",serviceMap);
    }

    public Map<String, CollateralServiceInterface<? extends CollateralModel,? extends Collateral>> getServiceMap() {
        return serviceMap;
    }
    public CollateralServiceInterface<? extends CollateralModel,? extends Collateral> getService(String code) {
        return serviceMap.get(code);
    }
}
