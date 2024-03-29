package com.bsuir.service;

import com.bsuir.dto.ChargeRequest;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentService {

    void charge(ChargeRequest chargeRequest, String username);

    BigDecimal getRevenue();
    Map<String, BigDecimal> getStaticsData();
}