package com.mcb.creditfactory.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author Vladimir Poddubchak @date 31.08.2020.
 */


public interface ApproveService {
    int approve(CollateralObject object);
    String getCode();

    @Autowired
    default void autoRegistration(ExternalApproveService service){
        service.registerService(this);
    }
}
