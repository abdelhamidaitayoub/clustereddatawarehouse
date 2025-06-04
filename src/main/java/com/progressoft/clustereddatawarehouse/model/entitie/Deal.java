package com.progressoft.clustereddatawarehouse.model.entitie;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "deals")
@Data
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Deal {

    @Id
    private String id;

    @NotNull(message = "From currency cannot be null")
    private Currency fromCurrency;

    @NotNull(message = "To currency cannot be null")
    private Currency toCurrency;

    @NotNull(message = "Timestamp cannot be null")
    private LocalDateTime timestamp;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;
}