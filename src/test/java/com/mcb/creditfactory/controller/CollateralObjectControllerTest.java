package com.mcb.creditfactory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.service.collateral.CollateralService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CollateralObjectController.class)
@AutoConfigureMockMvc
class CollateralObjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CollateralService collateralService;

    final ObjectMapper mapper = new ObjectMapper();

    @Test
    void save_WhenGivenCollateral_ThenReturn_Json() throws Exception {
        final AssessDto requestAssessDto = new AssessDto(null,null,null, LocalDateTime.parse("2020-09-02t12:00:00"), BigDecimal.valueOf(1100000),false);
        final CarDto requestDto = new CarDto(null,"Brand","Model",2000.,(short)2018,requestAssessDto);

        final UUID uuid = UUID.randomUUID();

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.saveCollateral(requestDto)).willReturn(uuid);

        mvc.perform(post("/collateral/save")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .equals(uuid.toString());
    }

    @Test
    void save_WhenGivenCollateralNotValid_ThenReturn_BadRequest() throws Exception {
        final AssessDto requestAssessDto = new AssessDto(null,null,null, LocalDateTime.parse("2020-09-02t12:00:00"), null,false);
        final CarDto requestDto = new CarDto(null,null,"Model",2000.,(short)2018,requestAssessDto);

        final UUID uuid = UUID.randomUUID();

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.saveCollateral(requestDto)).willThrow(IllegalArgumentException.class);

        mvc.perform(post("/collateral/save")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void save_WhenDataAccessException_ThenReturn_ServiceUnavailable() throws Exception {
        final AssessDto requestAssessDto = new AssessDto(null,null,CollateralType.CAR, LocalDateTime.parse("2020-09-02t12:00:00"), BigDecimal.valueOf(1_100_000),false);
        final CarDto requestDto = new CarDto(null,"Brand","Model",2000.,(short)2018,requestAssessDto);

        final UUID uuid = UUID.randomUUID();

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.saveCollateral(requestDto)).willThrow(DataAccessResourceFailureException.class);

        mvc.perform(post("/collateral/save")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void getInfo_WhenGivenCollateral_ThenReturn_Json() throws Exception {
        final AssessDto requestAssessDto = new AssessDto(null,null,null, LocalDateTime.parse("2020-09-02t12:01:01"), BigDecimal.valueOf(1100000),false);
        final CarDto requestDto = new CarDto(null,"Brand","Model",2000.,(short)2018,requestAssessDto);

        final UUID uuid = UUID.randomUUID();
        final AssessDto responseAssessDto = new AssessDto(1L,uuid, CollateralType.CAR, LocalDateTime.parse("2020-09-02t12:01:01"), BigDecimal.valueOf(1100000),true);
        final CarDto responseDto = new CarDto(uuid,"Brand","Model",2000.,(short)2018,responseAssessDto);

        final String json = mapper.writeValueAsString((Collateral)requestDto);

        given(collateralService.getCollateralInfo((Collateral)requestDto)).willReturn((Collateral)responseDto);

        mvc.perform(post("/collateral/info")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("car"))
                .andExpect(jsonPath("$.id").value(uuid.toString()))
                .andExpect(jsonPath("$.brand").value("Brand"))
                .andExpect(jsonPath("$.model").value("Model"))
                .andExpect(jsonPath("$.power").value("2000.0"))
                .andExpect(jsonPath("$.actualAssessDto.id").value("1"))
                .andExpect(jsonPath("$.actualAssessDto.collateralId").value(uuid.toString()))
                .andExpect(jsonPath("$.actualAssessDto.collateralType").value("CAR"))
                .andExpect(jsonPath("$.actualAssessDto.assessDate").value("2020-09-02T12:01:01"))
                .andExpect(jsonPath("$.actualAssessDto.value").value(1_100_000))
                .andExpect(jsonPath("$.actualAssessDto.status").value(true));
    }

    @Test
    void getInfo_WhenGivenCollateralNotValid_ThenReturn_BadRequest() throws Exception {
        final AssessDto requestAssessDto = new AssessDto(null,null,null, LocalDateTime.parse("2020-09-02t12:00:00"), null,false);
        final CarDto requestDto = new CarDto(null,null,"Model",2000.,(short)2018,requestAssessDto);

        final UUID uuid = UUID.randomUUID();

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.getCollateralInfo(requestDto)).willThrow(IllegalArgumentException.class);

        mvc.perform(post("/collateral/info")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getInfo_WhenDataAccessException_ThenReturn_ServiceUnavailable() throws Exception {
        final AssessDto requestAssessDto = new AssessDto(null,null,CollateralType.CAR, LocalDateTime.parse("2020-09-02t12:00:00"), BigDecimal.valueOf(1_100_000),false);
        final CarDto requestDto = new CarDto(null,"Brand","Model",2000.,(short)2018,requestAssessDto);

        final UUID uuid = UUID.randomUUID();

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.getCollateralInfo(requestDto)).willThrow(DataAccessResourceFailureException.class);

        mvc.perform(post("/collateral/info")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }




    @Test
    void addAssess_WhenGivenCollateral_ThenReturn_Json() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final AssessDto requestAssessDto = new AssessDto(null,uuid,CollateralType.CAR, LocalDateTime.parse("2020-09-02t12:01:01"), BigDecimal.valueOf(1200000),false);
        final CarDto requestDto = new CarDto(uuid,"Brand","Model",2000.,(short)2018,requestAssessDto);


        final AssessDto responseAssessDto = new AssessDto(2L,uuid, CollateralType.CAR, LocalDateTime.parse("2020-09-02t12:01:01"), BigDecimal.valueOf(1200000),true);
        final CarDto responseDto = new CarDto(uuid,"Brand","Model",2000.,(short)2018,responseAssessDto);

        final String json = mapper.writeValueAsString(requestAssessDto);

        given(collateralService.addAssess(requestAssessDto)).willReturn((Collateral)responseDto);

        mvc.perform(post("/collateral/addAssess")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("car"))
                .andExpect(jsonPath("$.id").value(uuid.toString()))
                .andExpect(jsonPath("$.brand").value("Brand"))
                .andExpect(jsonPath("$.model").value("Model"))
                .andExpect(jsonPath("$.power").value("2000.0"))
                .andExpect(jsonPath("$.actualAssessDto.id").value("2"))
                .andExpect(jsonPath("$.actualAssessDto.collateralId").value(uuid.toString()))
                .andExpect(jsonPath("$.actualAssessDto.collateralType").value("CAR"))
                .andExpect(jsonPath("$.actualAssessDto.assessDate").value("2020-09-02T12:01:01"))
                .andExpect(jsonPath("$.actualAssessDto.value").value(1_200_000))
                .andExpect(jsonPath("$.actualAssessDto.status").value(true));
    }

    @Test
    void addAssess_WhenGivenCollateralNotValid_ThenReturn_BadRequest() throws Exception {

        final AssessDto requestAssessDto = new AssessDto(null,null,null, LocalDateTime.parse("2020-09-02t12:00:00"), null,false);

        final String json = mapper.writeValueAsString(requestAssessDto);

        given(collateralService.addAssess(requestAssessDto)).willThrow(IllegalArgumentException.class);

        mvc.perform(post("/collateral/addAssess")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addAssess_WhenDataAccessException_ThenReturn_ServiceUnavailable() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final AssessDto requestAssessDto = new AssessDto(null,uuid,CollateralType.CAR, LocalDateTime.parse("2020-09-02t12:00:00"), BigDecimal.valueOf(1_300_000),false);

        final String json = mapper.writeValueAsString(requestAssessDto);

        given(collateralService.addAssess(requestAssessDto)).willThrow(DataAccessResourceFailureException.class);

        mvc.perform(post("/collateral/addAssess")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void getAssessList_WhenGivenCollateral_ThenReturn_Json() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final AssessDto requestAssessDto = new AssessDto(3L,uuid,CollateralType.CAR, LocalDateTime.parse("2020-09-03t12:01:01"), BigDecimal.valueOf(900_000),false);
        final CarDto requestDto = new CarDto(uuid,"Brand","Model",2000.,(short)2018,requestAssessDto);

        final AssessDto assessDto1 = new AssessDto(1L,uuid, CollateralType.CAR, LocalDateTime.parse("2020-09-01t12:01:01"), BigDecimal.valueOf(1_200_000),true);
        final AssessDto assessDto2 = new AssessDto(2L,uuid, CollateralType.CAR, LocalDateTime.parse("2020-09-02t12:01:01"), BigDecimal.valueOf(1_100_000),true);
        final AssessDto assessDto3 = new AssessDto(3L,uuid, CollateralType.CAR, LocalDateTime.parse("2020-09-03t12:01:01"), BigDecimal.valueOf(900_000),false);
        final List<AssessDto> responseAssessDtoList = List.of(assessDto3,assessDto2,assessDto1);
        System.out.println(responseAssessDtoList);

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.assessList((Collateral) requestDto)).willReturn(responseAssessDtoList);

        mvc.perform(post("/collateral/assessList")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].id").value("3"))
                .andExpect(jsonPath("$.[0].collateralId").value(uuid.toString()))
                .andExpect(jsonPath("$.[0].collateralType").value("CAR"))
                .andExpect(jsonPath("$.[0].assessDate").value("2020-09-03T12:01:01"))
                .andExpect(jsonPath("$.[0].value").value(900_000))
                .andExpect(jsonPath("$.[0].status").value(false))
                .andExpect(jsonPath("$.[2].id").value("1"))
                .andExpect(jsonPath("$.[2].collateralId").value(uuid.toString()))
                .andExpect(jsonPath("$.[2].collateralType").value("CAR"))
                .andExpect(jsonPath("$.[2].assessDate").value("2020-09-01T12:01:01"))
                .andExpect(jsonPath("$.[2].value").value(1_200_000))
                .andExpect(jsonPath("$.[2].status").value(true));
    }

    @Test
    void getAssessList_WhenGivenCollateralNotValid_ThenReturn_BadRequest() throws Exception {

        final AssessDto requestAssessDto = new AssessDto(null,null,null, LocalDateTime.parse("2020-09-02t12:00:00"), null,false);
        final CarDto requestDto = new CarDto(null,null,"Model",2000.,(short)2018,requestAssessDto);

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.assessList(requestDto)).willThrow(IllegalArgumentException.class);

        mvc.perform(post("/collateral/assessList")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAssessList_WhenDataAccessException_ThenReturn_ServiceUnavailable() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final AssessDto requestAssessDto = new AssessDto(3L,uuid,CollateralType.CAR, LocalDateTime.parse("2020-09-03t12:01:01"), BigDecimal.valueOf(900_000),false);
        final CarDto requestDto = new CarDto(uuid,"Brand","Model",2000.,(short)2018,requestAssessDto);

        final String json = mapper.writeValueAsString(requestDto);

        given(collateralService.assessList(requestDto)).willThrow(DataAccessResourceFailureException.class);

        mvc.perform(post("/collateral/assessList")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }
}