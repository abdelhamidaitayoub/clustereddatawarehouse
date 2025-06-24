package com.progressoft.clustereddatawarehouse.mapper;

import java.util.Currency;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;
import com.progressoft.clustereddatawarehouse.model.entity.Deal;

@Mapper(componentModel = "spring")
public interface DealMapper {

    @Mapping(target = "fromCurrency", source = "fromCurrency", qualifiedByName = "stringToCurrency")
    @Mapping(target = "toCurrency", source = "toCurrency", qualifiedByName = "stringToCurrency")
    Deal toEntity(DealDtoReq dealDtoReq);

    @Mapping(target = "fromCurrency", source = "fromCurrency", qualifiedByName = "currencyToString")
    @Mapping(target = "toCurrency", source = "toCurrency", qualifiedByName = "currencyToString")
    DealDtoRes toResponseDto(Deal deal);

    List<DealDtoRes> toResponseDtoList(List<Deal> deals);

    @Named("stringToCurrency")
    default Currency stringToCurrency(String currencyCode) {
        return currencyCode != null ? Currency.getInstance(currencyCode) : null;
    }

    @Named("currencyToString")
    default String currencyToString(Currency currency) {
        return currency != null ? currency.getCurrencyCode() : null;
    }
}