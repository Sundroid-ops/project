package com.demo.health_dekho_app.recommendation.DTO;

import java.util.List;

public record RecommendedProductsResponse(
        List<RecommendedProducts> recommendations
) {}

