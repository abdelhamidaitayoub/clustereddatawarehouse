package com.progressoft.clustereddatawarehouse.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.progressoft.clustereddatawarehouse.exception.DuplicateDealIdException;
import com.progressoft.clustereddatawarehouse.exception.InvalidCurrencyCodeException;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;
import com.progressoft.clustereddatawarehouse.model.entity.Deal;
import com.progressoft.clustereddatawarehouse.repositorie.DealRepository;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DealServiceImpl dealService;

    private DealDtoReq validRequest;
    private Deal dealEntity;
    private DealDtoRes expectedResponse;

    @BeforeEach
    void setup() {
        validRequest = new DealDtoReq();
        validRequest.setId("D123");
        validRequest.setFromCurrency("USD");
        validRequest.setToCurrency("EUR");
        validRequest.setTimestamp(LocalDateTime.now());
        validRequest.setAmount(BigDecimal.valueOf(1000));

        dealEntity = new Deal(
            "D123",
            Currency.getInstance("USD"),
            Currency.getInstance("EUR"),
            validRequest.getTimestamp(),
            validRequest.getAmount()
        );

        expectedResponse = new DealDtoRes();
        expectedResponse.setId("D123");
        expectedResponse.setFromCurrency(Currency.getInstance("USD"));
        expectedResponse.setToCurrency(Currency.getInstance("EUR"));
        expectedResponse.setTimestamp(validRequest.getTimestamp());
        expectedResponse.setAmount(validRequest.getAmount());
    }

    @Test
    void createDeal_Success() {
        when(dealRepository.existsById("D123")).thenReturn(false);
        when(modelMapper.map(validRequest, Deal.class)).thenReturn(dealEntity);
        when(dealRepository.save(any(Deal.class))).thenReturn(dealEntity);
        when(modelMapper.map(dealEntity, DealDtoRes.class)).thenReturn(expectedResponse);

        DealDtoRes actual = dealService.create(validRequest);

        assertEquals(expectedResponse, actual);
    }

    @Test
    void createDeal_DuplicateId_ThrowsException() {
        when(dealRepository.existsById("D123")).thenReturn(true);

        DuplicateDealIdException ex = assertThrows(DuplicateDealIdException.class,
            () -> dealService.create(validRequest));
        assertEquals("Request is already imported.", ex.getMessage());
    }

    @Test
    void createDeal_InvalidCurrency_ThrowsException() {
        validRequest.setFromCurrency("INVALID");

        InvalidCurrencyCodeException ex = assertThrows(InvalidCurrencyCodeException.class,
            () -> dealService.create(validRequest));
        assertTrue(ex.getMessage().contains("Invalid currency code"));
    }
}