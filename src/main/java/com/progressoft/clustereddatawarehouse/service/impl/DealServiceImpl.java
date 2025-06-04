package com.progressoft.clustereddatawarehouse.service.impl;



import java.util.Currency;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.progressoft.clustereddatawarehouse.exception.DuplicateDealIdException;
import com.progressoft.clustereddatawarehouse.exception.InvalidCurrencyCodeException;
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
    private final ModelMapper modelMapper;

    @Override
    public DealDtoRes create(final DealDtoReq dealRequestDto) {
        log.info("Attempting to create deal with ID: {}", dealRequestDto.getId());

        validateCurrencyCodes(dealRequestDto);

        if (dealRepository.existsById(dealRequestDto.getId())) {
            log.warn("Duplicate deal ID detected: {}", dealRequestDto.getId());
            throw new DuplicateDealIdException("Request is already imported.");
        }

        Deal deal = modelMapper.map(dealRequestDto, Deal.class);
        deal.setFromCurrency(Currency.getInstance(dealRequestDto.getFromCurrency()));
        deal.setToCurrency(Currency.getInstance(dealRequestDto.getToCurrency()));
        Deal savedDeal = dealRepository.save(deal);

        log.info("Deal created successfully with ID: {}", savedDeal.getId());
        return modelMapper.map(savedDeal, DealDtoRes.class);
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