package com.progressoft.clustereddatawarehouse.service.impl;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.springframework.stereotype.Service;

import com.progressoft.clustereddatawarehouse.exception.DuplicateDealIdException;
import com.progressoft.clustereddatawarehouse.exception.InvalidCurrencyCodeException;
import com.progressoft.clustereddatawarehouse.mapper.DealMapper;
import com.progressoft.clustereddatawarehouse.model.dto.BatchProcessingResult;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;
import com.progressoft.clustereddatawarehouse.model.entity.Deal;
import com.progressoft.clustereddatawarehouse.repositorie.DealRepository;
import com.progressoft.clustereddatawarehouse.service.inter.DealService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    @Override
    public DealDtoRes create(final DealDtoReq dealRequestDto) {
        log.info("Attempting to create deal with ID: {}", dealRequestDto.getId());

        validateCurrencyCodes(dealRequestDto);

        if (dealRepository.existsById(dealRequestDto.getId())) {
            log.warn("Duplicate deal ID detected: {}", dealRequestDto.getId());
            throw new DuplicateDealIdException("Request is already imported.");
        }

        Deal deal = dealMapper.toEntity(dealRequestDto);
        Deal savedDeal = dealRepository.save(deal);

        log.info("Deal created successfully with ID: {}", savedDeal.getId());
        return dealMapper.toResponseDto(savedDeal);
    }

    @Override
    public BatchProcessingResult createBatch(final List<DealDtoReq> dealRequests) {
        log.info("Starting batch processing for {} deals", dealRequests.size());

        List<DealDtoRes> successfulResults = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        for (DealDtoReq dealRequest : dealRequests) {
            try {
                DealDtoRes result = create(dealRequest);
                successfulResults.add(result);
                log.debug("Successfully processed deal: {}", dealRequest.getId());
            } catch (Exception e) {
                String errorMsg = String.format("Failed to process deal %s: %s",
                    dealRequest.getId(), e.getMessage());
                errorMessages.add(errorMsg);
                log.warn("Failed to process deal: {}", errorMsg);
            }
        }

        BatchProcessingResult result = new BatchProcessingResult(
            dealRequests.size(),
            successfulResults.size(),
            errorMessages.size(),
            successfulResults,
            errorMessages
        );

        log.info("Batch processing completed - Total: {}, Success: {}, Failed: {}",
            result.getTotalRequests(), result.getSuccessfulDeals(), result.getFailedDeals());

        return result;
    }

    @Override
    public List<DealDtoRes> getAllDeals() {
        log.info("Retrieving all deals from database");
        List<Deal> deals = dealRepository.findAll();
        List<DealDtoRes> dealDtos = dealMapper.toResponseDtoList(deals);
        log.info("Retrieved {} deals", dealDtos.size());
        return dealDtos;
    }

    private void validateCurrencyCodes(DealDtoReq dto) {
        try {
            Currency.getInstance(dto.getFromCurrency());
            Currency.getInstance(dto.getToCurrency());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid currency code provided: {}", ex.getMessage());
            throw new InvalidCurrencyCodeException("Invalid currency code provided");
        }
    }
}