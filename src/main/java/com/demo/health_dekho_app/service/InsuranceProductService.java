package com.demo.health_dekho_app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.health_dekho_app.DTO.AvailableProductResponse;
import com.demo.health_dekho_app.auth.Service.CurrentUserService;
import com.demo.health_dekho_app.mapper.InsuranceProductMapper;
import com.demo.health_dekho_app.repository.InsuranceProductRepository;


@Service
public class InsuranceProductService {

    private final InsuranceProductRepository insuranceProductRepository;
    private final CurrentUserService currentUserService;
    private final InsuranceProductMapper insuranceProductMapper;

    public InsuranceProductService(InsuranceProductRepository insuranceProductRepository,
            CurrentUserService currentUserService, InsuranceProductMapper insuranceProductMapper) {
        this.insuranceProductRepository = insuranceProductRepository;
        this.currentUserService = currentUserService;
        this.insuranceProductMapper = insuranceProductMapper;
    }


    @Transactional 
    public List<AvailableProductResponse> getAvailableProducts(int pageNumber, int PageSize) {

        Long customerId = currentUserService.getCurrentUserId();

        return insuranceProductRepository
                .findAvailableProducts(customerId, PageRequest.of(pageNumber, PageSize))
                .map(insuranceProductMapper::toAvailableProductResponse)
                .getContent();
    }
}
