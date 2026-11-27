package com.demo.health_dekho_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.health_dekho_app.DTO.CustomerPolicyResponse;
import com.demo.health_dekho_app.auth.Service.CurrentUserService;
import com.demo.health_dekho_app.entity.CustomerPolicyEntity;
import com.demo.health_dekho_app.mapper.CustomerPolicyMapper;
import com.demo.health_dekho_app.repository.CustomerPolicyRepository;

import java.util.List;

@Service
public class CustomerPolicyService {

    private final CustomerPolicyMapper customerPolicyMapper;
    private final CurrentUserService currentUserService;
    private final CustomerPolicyRepository customerPolicyRepository;

    public CustomerPolicyService(
            CustomerPolicyMapper customerPolicyMapper,
            CurrentUserService currentUserService,
            CustomerPolicyRepository customerPolicyRepository) {
        
        this.customerPolicyMapper = customerPolicyMapper;
        this.currentUserService = currentUserService;
        this.customerPolicyRepository = customerPolicyRepository;
    }

    @Transactional
    public List<CustomerPolicyResponse> getDashboard() {

        Long customerId = currentUserService.getCurrentUserId();

        List<CustomerPolicyEntity> policies =
                customerPolicyRepository
                    .findAllByCustomer_IdOrderByCreatedAtDesc(customerId);

        List<CustomerPolicyResponse> policyResponses =
                policies.stream()
                    .map(customerPolicyMapper::toCustomerPolicyResponse)
                    .toList();

        return policyResponses;
    }
}
