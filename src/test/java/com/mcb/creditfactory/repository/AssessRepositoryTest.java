package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.model.Assess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AssessRepositoryTest {

    @Autowired
    AssessRepository assessRepository;

    @BeforeEach
    public void setup() {
        this.assessRepository.deleteAll();
    }

    private UUID uuid0 = UUID.randomUUID();
    private UUID uuid1 = UUID.randomUUID();
    private UUID uuid2 = UUID.randomUUID();
    private UUID uuid3 = UUID.randomUUID();


    private Assess assess_1_1 = Assess.builder()
            .collateralId(uuid1)
            .type(CollateralType.CAR)
            .assessDate(LocalDateTime.now().minusMonths(1))
            .value(BigDecimal.valueOf(1_500_000))
            .status(true)
            .build();

    private Assess assess_2_1 = Assess.builder()
            .collateralId(uuid2)
            .type(CollateralType.CAR)
            .assessDate(LocalDateTime.now().minusMonths(2))
            .value(BigDecimal.valueOf(1_200_000))
            .status(true)
            .build();

    private Assess assess_2_2 = Assess.builder()
            .collateralId(uuid2)
            .type(CollateralType.CAR)
            .assessDate(LocalDateTime.now().minusWeeks(2))
            .value(BigDecimal.valueOf(900_000))
            .status(false)
            .build();

    private Assess assess_3_1 = Assess.builder()
            .collateralId(uuid3)
            .type(CollateralType.CAR)
            .assessDate(LocalDateTime.now().minusMonths(3))
            .value(BigDecimal.valueOf(1_500_000))
            .status(true)
            .build();
    private Assess assess_3_2 = Assess.builder()
            .collateralId(uuid3)
            .type(CollateralType.CAR)
            .assessDate(LocalDateTime.now().minusWeeks(3))
            .value(BigDecimal.valueOf(1_400_000))
            .status(true)
            .build();
    private Assess assess_3_3 = Assess.builder()
            .collateralId(uuid3)
            .type(CollateralType.CAR)
            .assessDate(LocalDateTime.now().minusDays(3))
            .value(BigDecimal.valueOf(900_000))
            .status(false)
            .build();


    @Test
    void findAllByCollateralId_whenInvalidCollateralId_ThenReturn_EmptyList() {
        assessRepository.save(assess_1_1);
        assessRepository.save(assess_2_1);
        assessRepository.save(assess_2_2);
        assessRepository.save(assess_3_1);
        assessRepository.save(assess_3_2);
        assessRepository.save(assess_3_3);

        assertEquals(assessRepository.findAllByCollateralId(uuid0).size(),0);
        System.out.println(assessRepository.findAllByCollateralId(uuid0));
    }

    @Test
    void findAllByCollateralId_whenValidCollateralId_ThenReturn_ValidList() {
        assessRepository.save(assess_1_1);
        assessRepository.save(assess_2_1);
        assessRepository.save(assess_2_2);
        assessRepository.save(assess_3_1);
        assessRepository.save(assess_3_2);
        assessRepository.save(assess_3_3);

        assertEquals(assessRepository.findAllByCollateralId(uuid1).size(),1);
        assertEquals(assessRepository.findAllByCollateralId(uuid2).size(),2);
        assertEquals(assessRepository.findAllByCollateralId(uuid3).size(),3);

        assertEquals(assessRepository.findAllByCollateralId(uuid3), List.of(assess_3_3,assess_3_2,assess_3_1));
    }

    @Test
    void findAllByCollateralId_whenValidCollateralId_ThenReturn_OrderedAssessList() {
        assessRepository.save(assess_1_1);
        assessRepository.save(assess_2_1);
        assessRepository.save(assess_2_2);
        assessRepository.save(assess_3_1);
        assessRepository.save(assess_3_2);
        assessRepository.save(assess_3_3);

        assertEquals(assessRepository.findAllByCollateralId(uuid3), List.of(assess_3_3,assess_3_2,assess_3_1));
        assertNotEquals(assessRepository.findAllByCollateralId(uuid3), List.of(assess_3_1,assess_3_2,assess_3_3));
        assertTrue(assessRepository.findAllByCollateralId(uuid3).get(0).getAssessDate().isAfter(assessRepository.findAllByCollateralId(uuid3).get(1).getAssessDate())
                &&(assessRepository.findAllByCollateralId(uuid3).get(1).getAssessDate().isAfter(assessRepository.findAllByCollateralId(uuid3).get(2).getAssessDate())));
    }

    @Test
    void findActualAssessByCollateralId_WhenValidCollateralId_ThenReturn_ActualAssess() {
        assessRepository.save(assess_1_1);
        assessRepository.save(assess_2_1);
        assessRepository.save(assess_2_2);
        assessRepository.save(assess_3_1);
        assessRepository.save(assess_3_2);
        assessRepository.save(assess_3_3);

        assertEquals(assessRepository.findActualAssessByCollateralId(uuid1), assess_1_1);
        assertEquals(assessRepository.findActualAssessByCollateralId(uuid2), assess_2_2);
        assertEquals(assessRepository.findActualAssessByCollateralId(uuid3), assess_3_3);
    }

    @Test
    void findActualAssessByCollateralId_WhenInvalidCollateralId_ThenReturn_Null() {
        assessRepository.save(assess_1_1);
        assessRepository.save(assess_2_1);
        assessRepository.save(assess_2_2);
        assessRepository.save(assess_3_1);
        assessRepository.save(assess_3_2);
        assessRepository.save(assess_3_3);

        assertEquals(assessRepository.findActualAssessByCollateralId(uuid0), null);
    }
}