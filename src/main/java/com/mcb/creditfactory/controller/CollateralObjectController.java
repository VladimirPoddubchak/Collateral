package com.mcb.creditfactory.controller;

import com.mcb.creditfactory.dto.AssessDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.service.CollateralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CollateralObjectController {
    @Autowired
    private CollateralService service;
/**
    curl -H "Content-Type: application/json" -X POST -d {\"type\":\"car\",\"brand\":\"UAZ\",\"model\":\"Patriot\",\"power\":1200,\"year\":2202,\"actualAssess\":{\"type\":\"CAR\",\"date\":\"2020-10-10 00:00:00\",\"value\":1500000}} http://localhost:8080/collateral/save
*/

    @Operation(summary = "Save collateral")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistic by day",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UUID.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid collateral supplied",
                content = @Content),
        @ApiResponse(responseCode = "503", description = "Service unavailable",
                content = @Content) })
    @PostMapping("/collateral/save")
    public HttpEntity<UUID> save(@RequestBody Collateral object) {
        UUID id = service.saveCollateral(object);
        return id != null ? ResponseEntity.ok(id) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/collateral/info")
    public HttpEntity<Collateral> getInfo(@RequestBody Collateral object) {
        Collateral info = service.getInfo(object);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }
}
