package com.progressoft.clustereddatawarehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;
import com.progressoft.clustereddatawarehouse.service.inter.DealService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/api/deals")
public class DealController {

    private final DealService dealService;
    
    @PostMapping
    public ResponseEntity<DealDtoRes> create( @Valid @RequestBody DealDtoReq dealDtoReq) {
        return new ResponseEntity<>(
            dealService.create(dealDtoReq),
            HttpStatus.CREATED
        );
    }
}