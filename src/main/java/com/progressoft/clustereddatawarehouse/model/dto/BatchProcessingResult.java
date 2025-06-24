package com.progressoft.clustereddatawarehouse.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchProcessingResult {
    private int totalRequests;
    private int successfulDeals;
    private int failedDeals;
    private List<DealDtoRes> successfulResults;
    private List<String> errorMessages;
}