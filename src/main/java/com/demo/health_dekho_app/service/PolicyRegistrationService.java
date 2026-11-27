package com.demo.health_dekho_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.health_dekho_app.DTO.RegistrationDetailsResponse;
import com.demo.health_dekho_app.auth.Service.CurrentUserService;
import com.demo.health_dekho_app.entity.CustomerEntity;
import com.demo.health_dekho_app.entity.InsuranceProductEntity;
import com.demo.health_dekho_app.exception.CustomerNotFoundException;
import com.demo.health_dekho_app.exception.ProductAlreadyRegisteredException;
import com.demo.health_dekho_app.exception.ProudctNotFoundOrNotActiveException;
import com.demo.health_dekho_app.mapper.RegistrationDetailsMapper;
import com.demo.health_dekho_app.repository.CustomerPolicyRepository;
import com.demo.health_dekho_app.repository.CustomerRepository;
import com.demo.health_dekho_app.repository.InsuranceProductRepository;


@Service 
public class PolicyRegistrationService {

    private final CustomerRepository customerRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final CustomerPolicyRepository customerPolicyRepository;
    private final CurrentUserService currentUserService;
    private final RegistrationDetailsMapper registrationDetailsMapper;

    public PolicyRegistrationService(CustomerRepository customerRepository,
            InsuranceProductRepository insuranceProductRepository, CustomerPolicyRepository customerPolicyRepository,
            CurrentUserService currentUserService, RegistrationDetailsMapper registrationDetailsMapper) {
        this.customerRepository = customerRepository;
        this.insuranceProductRepository = insuranceProductRepository;
        this.customerPolicyRepository = customerPolicyRepository;
        this.currentUserService = currentUserService;
        this.registrationDetailsMapper = registrationDetailsMapper;
    }

    @Transactional
    public RegistrationDetailsResponse getRegistrationDetails(Long productId) {

        Long customerId = currentUserService.getCurrentUserId();

        CustomerEntity customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        InsuranceProductEntity product =
            insuranceProductRepository.findByProductIdAndActiveTrue(productId)
                .orElseThrow(() -> new ProudctNotFoundOrNotActiveException("Active product not found"));

        boolean alreadyRegistered = 
            customerPolicyRepository.
                existsByCustomer_CustomerIdAndProduct_ProductId(customerId, productId);

        if (alreadyRegistered) {
            throw new ProductAlreadyRegisteredException(
                "You have already registered for this product"
            );
        }

        return registrationDetailsMapper.toResponse(customer, product);
    }
}
