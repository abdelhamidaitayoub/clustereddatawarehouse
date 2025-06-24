package com.progressoft.clustereddatawarehouse.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progressoft.clustereddatawarehouse.model.dto.BatchProcessingResult;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoReq;
import com.progressoft.clustereddatawarehouse.model.dto.DealDtoRes;
import com.progressoft.clustereddatawarehouse.service.inter.DealService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DealController.class)
class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealService dealService;

    @Autowired
    private ObjectMapper objectMapper;

    private DealDtoReq dealRequest;
    private DealDtoRes dealResponse;

    @BeforeEach
    void setUp() {
        dealRequest = new DealDtoReq();
        dealRequest.setId("FX001");
        dealRequest.setFromCurrency("USD");
        dealRequest.setToCurrency("EUR");
        dealRequest.setTimestamp(LocalDateTime.now());
        dealRequest.setAmount(BigDecimal.valueOf(1000000));

        dealResponse = new DealDtoRes();
        dealResponse.setId("FX001");
        dealResponse.setAmount(BigDecimal.valueOf(1000000));
    }

    @Test
    void createDeal_Success() throws Exception {
        when(dealService.create(any(DealDtoReq.class))).thenReturn(dealResponse);

        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dealRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("FX001"))
                .andExpect(jsonPath("$.amount").value(1000000));
    }

    @Test
    void createBatch_Success() throws Exception {
        List<DealDtoReq> batchRequest = Arrays.asList(dealRequest);
        BatchProcessingResult batchResult = new BatchProcessingResult(
            1, 1, 0, Arrays.asList(dealResponse), Arrays.asList()
        );

        when(dealService.createBatch(anyList())).thenReturn(batchResult);

        mockMvc.perform(post("/api/deals/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(batchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRequests").value(1))
                .andExpect(jsonPath("$.successfulDeals").value(1))
                .andExpect(jsonPath("$.failedDeals").value(0));
    }

    @Test
    void getAllDeals_Success() throws Exception {
        List<DealDtoRes> deals = Arrays.asList(dealResponse);
        when(dealService.getAllDeals()).thenReturn(deals);

        mockMvc.perform(get("/api/deals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("FX001"));
    }

    @Test
    void createDeal_InvalidRequest_BadRequest() throws Exception {
        DealDtoReq invalidRequest = new DealDtoReq();
        invalidRequest.setId(""); // Empty ID should fail validation

        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}