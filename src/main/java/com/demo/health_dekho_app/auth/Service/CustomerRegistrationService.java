package com.demo.health_dekho_app.auth.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.health_dekho_app.auth.DTO.SignupRequest;
import com.demo.health_dekho_app.auth.DTO.SignupResponse;
import com.demo.health_dekho_app.entity.CustomerEntity;
import com.demo.health_dekho_app.entity.enums.CustomerStatus;
import com.demo.health_dekho_app.exception.CustomerAlreadyExistsException;
import com.demo.health_dekho_app.repository.CustomerRepository;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerRegistrationService(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {

        String email = request.email().trim().toLowerCase();

        if (customerRepository.existsByEmailIgnoreCase(email)) {
            throw new CustomerAlreadyExistsException(
                "Email is already registered"
            );
        }

        CustomerEntity customer = new CustomerEntity();

        customer.setFullName(request.fullName().trim());
        customer.setEmail(email);

        customer.setPasswordHash(
            passwordEncoder.encode(request.password())
        );

        customer.setDateOfBirth(request.dob());
        customer.setPhone(request.phone());
        customer.setStatus(CustomerStatus.ACTIVE);

        CustomerEntity savedCustomer =
            customerRepository.save(customer);

        return new SignupResponse(
            savedCustomer.getId(),
            savedCustomer.getFullName(),
            savedCustomer.getEmail(),
            "Account created successfully. Please log in."
        );
    }
}