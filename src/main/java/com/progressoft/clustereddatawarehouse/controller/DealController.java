package com.progressoft.clustereddatawarehouse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressoft.clustereddatawarehouse.model.dto.BatchProcessingResult;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;
import com.progressoft.clustereddatawarehouse.service.inter.DealService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/deals")
@Tag(name = "FX Deals", description = "Foreign Exchange Deals Management")
public class DealController {

    private final DealService dealService;

    @Operation(summary = "Create a single FX deal", description = "Creates and persists a single foreign exchange deal")
    @PostMapping
    public ResponseEntity<DealDtoRes> create(@Valid @RequestBody DealDtoReq dealDtoReq) {
        return new ResponseEntity<>(
            dealService.create(dealDtoReq),
            HttpStatus.CREATED
        );
    }

    @Operation(summary = "Batch process multiple FX deals", description = "Processes multiple deals at once with no rollback policy")
    @PostMapping("/batch")
    public ResponseEntity<BatchProcessingResult> createBatch(@Valid @RequestBody List<DealDtoReq> dealRequests) {
        return new ResponseEntity<>(
            dealService.createBatch(dealRequests),
            HttpStatus.OK
        );
    }

    @Operation(summary = "Get all deals", description = "Retrieves all persisted FX deals")
    @GetMapping
    public ResponseEntity<List<DealDtoRes>> getAllDeals() {
        return ResponseEntity.ok(dealService.getAllDeals());
    }
}