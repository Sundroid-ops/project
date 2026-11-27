package com.demo.health_dekho_app.DTO;

import java.math.BigDecimal;

public record AvailableProductResponse(
        Long productId,
        String productCode,
        String policyName,
        String policyType,
        Integer termMonths,
        BigDecimal sumInsured,
        String premiumFrequency,
        BigDecimal premiumAmount
) {}
