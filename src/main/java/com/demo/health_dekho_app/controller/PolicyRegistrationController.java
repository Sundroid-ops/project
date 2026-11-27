package com.demo.health_dekho_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.health_dekho_app.DTO.RegistrationDetailsResponse;
import com.demo.health_dekho_app.service.PolicyRegistrationService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/products")
public class PolicyRegistrationController {

    private final PolicyRegistrationService policyRegistrationService;

    public PolicyRegistrationController(PolicyRegistrationService policyRegistrationService) {
        this.policyRegistrationService = policyRegistrationService;
    }

    @GetMapping ("/{productId}/registration-details")
    public ResponseEntity<RegistrationDetailsResponse> getRegistrationDetails(
        @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
            policyRegistrationService.getRegistrationDetails(productId)
        );
    }
}