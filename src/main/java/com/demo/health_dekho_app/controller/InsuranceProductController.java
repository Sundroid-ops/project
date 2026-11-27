package com.demo.health_dekho_app.controller;

import com.demo.health_dekho_app.DTO.AvailableProductResponse;
import com.demo.health_dekho_app.service.InsuranceProductService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class InsuranceProductController {

    private final InsuranceProductService insuranceProductService;

    public InsuranceProductController(
            InsuranceProductService insuranceProductService) {
        this.insuranceProductService = insuranceProductService;
    }

    @GetMapping("/available")
    public ResponseEntity<List<AvailableProductResponse>> getAvailableProducts(
            @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(
                insuranceProductService.getAvailableProducts(pageNumber, pageSize)
        );
    }
}