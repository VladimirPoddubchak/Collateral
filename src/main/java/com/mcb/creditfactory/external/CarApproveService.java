package com.mcb.creditfactory.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by @author Vladimir Poddubchak @date 31.08.2020.
 */
@Component
public class CarApproveService implements ApproveService {

    @Autowired
    Environment env;

//    private LocalDate MIN_ASSESS_DATE = LocalDate.parse(env.getProperty("car.minAssessDate"));
//    private  int MIN_YEAR = Integer.parseInt(env.getProperty("car.minYear"));
//    private  BigDecimal MIN_VALUE = BigDecimal.valueOf (Long.parseLong(env.getProperty("car.minValue")));

    @Override
    public int approve(CollateralObject object) {

        if (object.getYear() < Integer.parseInt(env.getProperty("car.minYear"))) {
            return -10;
        }
        if (object.getDate().isBefore(LocalDate.parse(env.getProperty("car.minAssessDate")))) {
            return -11;
        }
        if (object.getValue().compareTo(BigDecimal.valueOf (Long.parseLong(env.getProperty("car.minValue")))) < 0) {
            return -12;
        }

        return 0;
    }

    @Override
    public String getCode() {
        return CollateralType.CAR.toString();
    }
}
