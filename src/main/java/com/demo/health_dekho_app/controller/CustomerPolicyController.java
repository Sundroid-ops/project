package com.demo.health_dekho_app.controller;

import com.demo.health_dekho_app.DTO.CustomerPolicyResponse;
import com.demo.health_dekho_app.service.CustomerPolicyService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/dashboard")
public class CustomerPolicyController {

    private final CustomerPolicyService dashboardService;

    public CustomerPolicyController(CustomerPolicyService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/registered")
    public ResponseEntity<List<CustomerPolicyResponse>> getDashboard() {
        
        return ResponseEntity.ok(dashboardService.getDashboard());
    }
}