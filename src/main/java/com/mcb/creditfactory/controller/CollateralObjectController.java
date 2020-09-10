package com.mcb.creditfactory.controller;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.service.collateral.CollateralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
public class CollateralObjectController {
    @Autowired
    private CollateralService service;
/**
    curl -H "Content-Type: application/json" -X POST -d "{\"type\":\"car\",\"brand\":\"Toyota\",\"model\":\"Prado\",\"power\":2200,\"year\":2019,\"actualAssessDto\":{\"type\":\"CAR\",\"assessDate\":\"2020-10-10t00:00:00\",\"value\":3000000}}" http://localhost:8080/collateral/save
    curl -X POST "http://localhost:8080/collateral/save" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"type\":\"airplane\",\"brand\":\"BrandA\",\"model\":\"ModelA\",\"manufacturer\":\"Inc.A\",\"year\":\"2015\",\"fuelCapasity\":\"20000\",\"seats\":\"25\",\"actualAssessDto\":{\"collateralType\":\"AIRPLANE\",\"assessDate\":\"2020-09-10T11:16:53.628Z\",\"value\":25000000}}"
 */

    @Operation(summary = "Save collateral",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "One of Dto, implements 'Collateral' and 'CollateralObject' interface: CarDto, AirplaneDto",
                    required = true,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    description = "Don,t understand how make OpenApi show not interface,but classes implements interface",
                                    anyOf = {CarDto.class, AirplaneDto.class}),
                            examples = @ExampleObject(
                                    "{\n" +
                                    "  \"type\": \"car\",\n" +
                                    "  \"brand\": \"BrandC\",\n" +
                                    "  \"model\": \"ModelC\",\n" +
                                    "  \"power\": \"1200\",\n" +
                                    "  \"year\": \"2015\",\n" +
                                    "  \"actualAssessDto\": \n" +
                                    "  {\n" +
                                    "    \"collateralType\": \"CAR\",\n" +
                                    "    \"assessDate\": \"2020-09-08T11:16:53.628Z\",\n" +
                                    "    \"value\": 1000000\n" +
                                    "  }\n" +
                                    "}")
                            )}),
            responses = {
                    @ApiResponse (responseCode = "200", description = "Collateral saved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema( anyOf = {CarDto.class, AirplaneDto.class}),
                                    examples = @ExampleObject(
                                            "{\n" +
                                            "  \"type\": \"car\",\n" +
                                            "  \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                            "  \"brand\": \"Brand\",\n" +
                                            "  \"model\": \"Model\",\n" +
                                            "  \"power\": \"1200\",\n" +
                                            "  \"year\": \"2015\",\n" +
                                            "  \"actualAssessDto\": \n" +
                                            "  {\n" +
                                            "    \"id\": \"22\",\n" +
                                            "    \"collateralId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                            "    \"collateralType\": \"CAR\",\n" +
                                            "    \"assessDate\": \"2020-09-08T11:16:53.628Z\",\n" +
                                            "    \"value\": 1000000,\n" +
                                            "    \"status\": true\n" +
                                            "  }\n" +
                                            "}")
                            )),
                    @ApiResponse (responseCode = "400", description = "Invalid collateral supplied", content = @Content),
                    @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content)
            })
    @PostMapping("/collateral/save")
    public<D extends Collateral> HttpEntity<UUID> save( @RequestBody D collateralDto) {

        UUID result = null;

        try {
            result = service.saveCollateral(collateralDto);
        }catch (IllegalArgumentException ex){
            log.warn(ex+" collateralDto "+collateralDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid collateralDto supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        return ResponseEntity.ok(result);
    }

/**
    curl -H "Content-Type: application/json" -X POST -d "{\"type\":\"car\",\"brand\":\"UAZ\",\"model\":\"Patriot\",\"power\":1200,\"year\":2200,\"actualAssess\":{\"type\":\"CAR\",\"assessDate\":\"2020-10-10t00:00:00\",\"value\":1500000}}" http://localhost:8080/collateral/info
*/

    @Operation(summary = "Get actual collateral info",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "One of Dto, implements 'Collateral' and 'CollateralObject' interface: CarDto, AirplaneDto",
                    required = true,
                    content = { @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    "{\n" +
                                    "  \"type\": \"car\",\n" +
                                    "  \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\"\n" +
                                    "}"),
                            schema = @Schema(anyOf = {CarDto.class, AirplaneDto.class}))}),
            responses = {@ApiResponse(responseCode = "200", description = "Actual collateral info",
                    content = { @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    "{\n" +
                                    "  \"type\": \"car\",\n" +
                                    "  \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                    "  \"brand\": \"Brand\",\n" +
                                    "  \"model\": \"Model\",\n" +
                                    "  \"power\": \"1200\",\n" +
                                    "  \"year\": \"2015\",\n" +
                                    "  \"actualAssessDto\": \n" +
                                    "  {\n" +
                                    "    \"id\": \"22\",\n" +
                                    "    \"collateralId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                    "    \"collateralType\": \"CAR\",\n" +
                                    "    \"assessDate\": \"2020-09-08T11:16:53.628Z\",\n" +
                                    "    \"value\": 1000000,\n" +
                                    "    \"status\": true\n" +
                                    "  }\n" +
                                    "}"),
                            schema = @Schema(anyOf = {CarDto.class, AirplaneDto.class})) }),
                    @ApiResponse(responseCode = "400", description = "Invalid collateral supplied", content = @Content),
                    @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content) }
    )
    @PostMapping("/collateral/info")
    public<D extends Collateral> HttpEntity<? extends Collateral> getInfo(@RequestBody D collateralDto) {

        Collateral result = null;

        try {
            result = service.getCollateralInfo(collateralDto);
        }catch (IllegalArgumentException ex){
            log.warn(ex+" collateralDto "+collateralDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid collateralDto supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        return ResponseEntity.ok(result);
    }


    /**
        curl -X POST "http://localhost:8080/collateral/addAssess" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"collateralId\":\"ea1236bc-24e8-4601-971b-e3349b5b0c25\",\"collateralType\":\"CAR\",\"assessDate\":\"2020-08-31T15:15:00.688Z\",\"value\":950000}"
     *
     * @param assessDto
     * @return
     */
    @Operation(summary = "Add assess",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "AssessDto",
                    required = true,
                    content = { @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("{\n" +
                                    "  \"collateralId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                    "  \"collateralType\": \"CAR\",\n" +
                                    "  \"assessDate\": \"2020-09-08T11:16:53.628Z\",\n" +
                                    "  \"value\": 1000000\n" +
                                    "}"),
                            schema = @Schema(implementation = AssessDto.class))}),
            responses = {@ApiResponse(responseCode = "200", description = "Add assess to collateral",
                    content = { @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    "{\n" +
                                            "  \"type\": \"car\",\n" +
                                            "  \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                            "  \"brand\": \"Brand\",\n" +
                                            "  \"model\": \"Model\",\n" +
                                            "  \"power\": \"1200\",\n" +
                                            "  \"year\": \"2015\",\n" +
                                            "  \"actualAssessDto\": \n" +
                                            "  {\n" +
                                            "    \"id\": \"22\",\n" +
                                            "    \"collateralId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                            "    \"collateralType\": \"CAR\",\n" +
                                            "    \"assessDate\": \"2020-09-08T11:16:53.628Z\",\n" +
                                            "    \"value\": 1000000,\n" +
                                            "    \"status\": true\n" +
                                            "  }\n" +
                                            "}"),
                            schema = @Schema(anyOf = {CarDto.class, AirplaneDto.class})) }),
                    @ApiResponse(responseCode = "400", description = "Invalid assess supplied", content = @Content),
                    @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content) }
    )
    @PostMapping("/collateral/addAssess")
    public HttpEntity<? extends Collateral> addAssess(@RequestBody AssessDto assessDto) {

        Collateral result = null;

        try {
            result = service.addAssess(assessDto);
        }catch (IllegalArgumentException ex){
            log.warn(ex+" assessDto "+assessDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid assessDto supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Get list of assesses for collateral",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "One of Dto, implements 'Collateral' and 'CollateralObject' interface: CarDto, AirplaneDto",
                    required = true,
                    content = { @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("{\n" +
                                    "  \"type\": \"car\",\n" +
                                    "  \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\"\n" +
                                    "}"),
                            schema = @Schema(anyOf = {CarDto.class, AirplaneDto.class}))}),
            responses = {@ApiResponse(responseCode = "200", description = "Add assess to collateral",
                    content = { @Content(mediaType = "application/json",
                            examples = @ExampleObject(description = "Sorted by date(desc) list of assesses",value =
                                   "{\n" +
                                    "  {\n" +
                                    "    \"id\": \"1\",\n" +
                                    "    \"collateralId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                    "    \"collateralType\": \"CAR\",\n" +
                                    "    \"assessDate\": \"2020-10-08T11:16:53.628Z\",\n" +
                                    "    \"value\": 9500000,\n" +
                                    "    \"status\": false\n" +
                                    "  }\n" +
                                    "  {\n" +
                                    "    \"id\": \"5\",\n" +
                                    "    \"collateralId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                                    "    \"collateralType\": \"CAR\",\n" +
                                    "    \"assessDate\": \"2020-09-08T11:16:53.628Z\",\n" +
                                    "    \"value\": 1000000,\n" +
                                    "    \"status\": true\n" +
                                    "  }\n" +
                                    "}"),
                            schema = @Schema(implementation = AssessDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid collateral supplied", content = @Content),
                    @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content) }
    )
    @PostMapping("/collateral/assessList")
    public <D extends Collateral> HttpEntity<List<AssessDto>> getAssessList(@RequestBody D collateralDto) {

        List<AssessDto> result = null;

        try {
            result = service.assessList(collateralDto);
        }catch (IllegalArgumentException ex){
            log.warn(ex+" collateralDto "+collateralDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid collateralDto supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        return ResponseEntity.ok(result);
    }
}
