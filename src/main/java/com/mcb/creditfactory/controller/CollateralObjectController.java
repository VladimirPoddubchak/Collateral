package com.mcb.creditfactory.controller;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.service.CollateralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

 */

    @Operation(summary = "Save collateral")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Collateral saved",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UUID.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid collateral supplied",
                content = @Content),
        @ApiResponse(responseCode = "503", description = "Service unavailable",
                content = @Content) })
    @PostMapping("/collateral/save")
        public HttpEntity<UUID> save( @RequestBody Collateral collateralDto) {

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
//
//
//
//        UUID id = service.saveCollateral(collateralDto);
//        return id != null ? ResponseEntity.ok(id) : ResponseEntity.badRequest().build();
    }

/**
    curl -H "Content-Type: application/json" -X POST -d "{\"type\":\"car\",\"brand\":\"UAZ\",\"model\":\"Patriot\",\"power\":1200,\"year\":2200,\"actualAssess\":{\"type\":\"CAR\",\"assessDate\":\"2020-10-10t00:00:00\",\"value\":1500000}}" http://localhost:8080/collateral/info
*/

    @Operation(summary = "Get actual collateral info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actual collateral info",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid collateral supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content) })
    @PostMapping("/collateral/info")
    public HttpEntity<Collateral> getInfo(@RequestBody Collateral collateralDto) {

        Collateral result = null;

        try {
            result = service.getInfo(collateralDto);
        }catch (IllegalArgumentException ex){
            log.warn(ex+" collateralDto "+collateralDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid collateralDto supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        return ResponseEntity.ok(result);

//        Collateral info = service.getInfo(objectDto);
//        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }


    /**
        curl -X POST "http://localhost:8080/collateral/addAssess" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"collateralId\":\"ea1236bc-24e8-4601-971b-e3349b5b0c25\",\"collateralType\":\"CAR\",\"assessDate\":\"2020-08-31T15:15:00.688Z\",\"value\":950000}"
     *
     * @param assessDto
     * @return
     */

    @Operation(summary = "Add asses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assess added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Assess supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content) })
    @PostMapping("/collateral/addAssess")
    public HttpEntity<Collateral> addAssess(@RequestBody AssessDto assessDto) {

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

//
//        Collateral result = service.addAssess(assessDto);
//        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get list of assesses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assess list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid collateral supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content) })
    @PostMapping("/collateral/assessList")
    public HttpEntity<List<AssessDto>> getAssessList(@RequestBody Collateral collateralDto) {

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


//
//        List<AssessDto> result = service.assessList(collateralDto);
//        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
}
