package com.progressoft.clustereddatawarehouse.service.inter;

import java.util.List;

import com.progressoft.clustereddatawarehouse.model.dto.BatchProcessingResult;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;

public interface DealService {

    DealDtoRes create(final DealDtoReq dealRequestDto);

    BatchProcessingResult createBatch(final List<DealDtoReq> dealRequests);

    List<DealDtoRes> getAllDeals();
}