package com.progressoft.clustereddatawarehouse.service.inter;

import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;

public interface DealService {

    DealDtoRes create(final DealDtoReq dealRequestDto);
}