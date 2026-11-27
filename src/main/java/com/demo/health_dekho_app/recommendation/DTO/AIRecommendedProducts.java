package com.demo.health_dekho_app.recommendation.DTO;

public record AIRecommendedProducts(
        Long productId,
        String RecommendationReason,
        String suitability,
        Integer rankOrder
) {}