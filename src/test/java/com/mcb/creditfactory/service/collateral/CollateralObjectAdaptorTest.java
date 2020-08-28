package com.mcb.creditfactory.service.collateral;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CollateralObjectAdaptorTest {


//    @Test
//    void getTest() {
//        final CarDto carDto= new CarDto(null,"Audi","Q3",18000.,(short)2016,
//            new AssessDto(null,null, CollateralType.CAR, LocalDateTime.parse("2020-06-01t14:00:00"), BigDecimal.valueOf(1_400_000),false));
//
//        System.out.println(carDto);
//        CollateralObjectAdaptor adaptor = new CollateralObjectAdaptor((Collateral) carDto);
//        System.out.println(carDto);
//        System.out.println(adaptor);
//    }

//
//    final private Collateral collateral= (Collateral) carDto;
//
//    final private CollateralObjectAdaptor adaptor = new CollateralObjectAdaptor(collateral);
//    @Test
//    void getValue() {
//        assertEquals(adaptor.getValue(),carDto.getActualAssess().getValue());
//    }
//
//    @Test
//    void getYear() {
//        assertEquals(adaptor.getYear(),carDto.getYear());
//    }
//
//    @Test
//    void getDate() {
//        assertEquals(adaptor.getValue(),carDto.getActualAssess().getAssessDate());
//    }
//
//    @Test
//    void getType() {
//        assertEquals(adaptor.getType(),carDto.getType());
//
//    }
}