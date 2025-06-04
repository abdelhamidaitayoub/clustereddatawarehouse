package com.progressoft.clustereddatawarehouse.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import lombok.Data;

@Data
public class DealDtoRes {
    private String id;

    private Currency fromCurrency;

    private Currency toCurrency;

    private LocalDateTime timestamp;

    private BigDecimal amount;
}
