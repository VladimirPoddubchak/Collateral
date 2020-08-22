package com.mcb.creditfactory.service.collateral;

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


    private Map<String, CollateralServiceInterface<?,?>> serviceMap = new ConcurrentHashMap<>();

    public void registerService(CollateralServiceInterface<?,?> service) {
        serviceMap.put(service.getCode(),service);
        log.info("Collateral service: {} registered by key: {}",serviceMap.get(service.getCode()),service.getCode());
    }

    public Map<String, CollateralServiceInterface<?,?>> getServiceMap() {
        return serviceMap;
    }




}
