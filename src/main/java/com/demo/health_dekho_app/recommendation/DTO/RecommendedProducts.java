package com.demo.health_dekho_app.recommendation.DTO;

import java.math.BigDecimal;

public record RecommendedProducts(
        Long productId,
        String productCode,
        String policyName,
        String policyType,
        Integer termMonths,
        BigDecimal sumInsured,
        String premiumFrequency,
        BigDecimal premiumAmount,
        String recommendationReason,
        String suitability,
        Integer rankOrder
) {}