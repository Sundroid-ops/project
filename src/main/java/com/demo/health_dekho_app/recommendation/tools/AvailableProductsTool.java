package com.demo.health_dekho_app.recommendation.tools;

import com.demo.health_dekho_app.service.InsuranceProductService;
import com.demo.health_dekho_app.DTO.AvailableProductResponse;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvailableProductsTool {

    private final InsuranceProductService productService;

    public AvailableProductsTool(InsuranceProductService productService) {
        this.productService = productService;
    }

    @Tool(description = """
            Fetch available insurance products from the database.
            Use pageNumber and pageSize to retrieve products.
            Use this when the initially provided products are insufficient.
            """)
    public List<AvailableProductResponse> getAvailableProducts(
            @ToolParam(description = "Zero-based page number")
            int pageNumber,

            @ToolParam(description = "Number of products to fetch")
            int pageSize
    ) {
        pageSize = Math.min(Math.max(pageSize, 1), 10);
        pageNumber = Math.max(pageNumber, 0);

        return productService.getAvailableProducts(pageNumber, pageSize);
    }
}