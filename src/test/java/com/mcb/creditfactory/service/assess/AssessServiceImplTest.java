package com.mcb.creditfactory.service.assess;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.model.Assess;
import com.mcb.creditfactory.repository.AssessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class AssessServiceImplTest {
    @TestConfiguration
    static class AssessServiceImplTestContextConfiguration{

        @Bean
        public AssessService     assessService(){
            return new AssessServiceImpl();
        }

    }

    @Autowired
    private AssessService assessService;

    @MockBean
    private AssessRepository assessRepository;

    final UUID uuid0=UUID.randomUUID();
    final UUID uuid1=UUID.randomUUID();
    final UUID uuid2=UUID.randomUUID();

    final UUID uuidIsNull = null;
    final Assess assess1 = new Assess(1L, uuid1, CollateralType.CAR, LocalDateTime.now().minusMonths(1), BigDecimal.valueOf(1_500_00),true);
    final Assess assess2 = new Assess(2L, uuid1, CollateralType.CAR, LocalDateTime.now().minusWeeks(1), BigDecimal.valueOf(900_00),false);
    final Assess assess3 = new Assess(3L, uuid2, CollateralType.CAR, LocalDateTime.now().minusWeeks(1), BigDecimal.valueOf(2_000_00),true);

    final AssessDto dto1 = new AssessDto(1L, uuid1,CollateralType.CAR, LocalDateTime.now().minusMonths(1), BigDecimal.valueOf(1_500_00),true);


    @BeforeEach
    public void setUp(){
        Mockito.when(assessRepository.save(assess1)).thenReturn(assess1);
        Mockito.when(assessRepository.findActualAssessByCollateralId(uuid1)).thenReturn(assess2);
        Mockito.when(assessRepository.findActualAssessByCollateralId(uuid2)).thenReturn(assess3);
        Mockito.when(assessRepository.findActualAssessByCollateralId(uuid0)).thenReturn(null);
        Mockito.when(assessRepository.findAllByCollateralId(uuid1)).thenReturn(List.of(assess2,assess1));
        Mockito.when(assessRepository.findAllByCollateralId(uuid2)).thenReturn(List.of(assess3));
        Mockito.when(assessRepository.findAllByCollateralId(uuid0)).thenReturn(List.of());
    }

    @Test
    void getActualAssessDto_WhenUuidIsNull_ThenThrow_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,()-> assessService.getActualAssessDto(uuidIsNull));
    }

    @Test
    void getActualAssessDto_WhenUuidIsInvalid_ThenThrow_NoSuchElementException() {
        assertThrows(NoSuchElementException.class,()-> assessService.getActualAssessDto(uuid0));
    }

    @Test
    void getActualAssessDto_WhenUuidIsValid_ThenReturn_AssessDto() {
        assertEquals(assessService.getActualAssessDto(uuid1),assessService.toDto(assess2));
        assertEquals(assessService.getActualAssessDto(uuid2),assessService.toDto(assess3));
        assertNotEquals(assessService.getActualAssessDto(uuid1),assessService.toDto(assess1));
    }

    @Test
    void getAssessDtoList_WhenUuidIsNull_ThenThrow_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,()-> assessService.getAssessDtoList(uuidIsNull));
    }

    @Test
    void getAssessDtoList_WhenUuidIsInvalid_ThenThrow_NoSuchElementException()  {
        assertThrows(NoSuchElementException.class,()-> assessService.getAssessDtoList(uuid0));
    }

    @Test
    void getAssessDtoList_WhenUuidIsValid_ThenReturn_AssessDtoList()  {
        assertEquals(assessService.getAssessDtoList(uuid1),List.of(assessService.toDto(assess2),assessService.toDto(assess1)));
        assertEquals(assessService.getAssessDtoList(uuid2),List.of(assessService.toDto(assess3)));
    }
    @Test
    void fromDto() {

        Assess fromDtoAssess =assessService.fromDto(dto1);

        assertNull(fromDtoAssess.getId());
        assertEquals(fromDtoAssess.getCollateralId(),assess1.getCollateralId());
        assertEquals(fromDtoAssess.getType(),assess1.getType());
        assertEquals(fromDtoAssess.getValue(),assess1.getValue());
        assertEquals(fromDtoAssess.getStatus(),assess1.getStatus());

        Duration duration = Duration.between(assessService.fromDto(dto1).getAssessDate(), assess1.getAssessDate());
        assertEquals(0, duration.toSeconds());

    }

    @Test
    void toDto() {
        AssessDto assessDto =assessService.toDto(assess1);

        assertEquals(assessDto.getId(),dto1.getId());
        assertEquals(assessDto.getCollateralId(),dto1.getCollateralId());
        assertEquals(assessDto.getCollateralType(),dto1.getCollateralType());
        assertEquals(assessDto.getValue(),dto1.getValue());
        assertEquals(assessDto.isStatus(),dto1.isStatus());

        Duration duration = Duration.between(assessService.toDto(assess1).getAssessDate(), assess1.getAssessDate());
        assertEquals(0, duration.toSeconds());

    }


    @Test
    void save() {
    }
}