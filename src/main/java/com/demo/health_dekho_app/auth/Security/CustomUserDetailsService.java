package com.demo.health_dekho_app.auth.Security;

import com.demo.health_dekho_app.entity.CustomerEntity;
import com.demo.health_dekho_app.entity.enums.CustomerStatus;
import com.demo.health_dekho_app.repository.CustomerRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(
            CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        CustomerEntity customer = customerRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                    new UsernameNotFoundException("Invalid credentials"));

        return new CustomerPrincipal(customer);
    }

    public CustomerPrincipal loadUserById(Long id) {
        CustomerEntity customer = customerRepository
                .findByIdAndStatus(id, CustomerStatus.ACTIVE)
                .orElseThrow(() ->
                    new UsernameNotFoundException("Customer not found"));

        return new CustomerPrincipal(customer);
    }
}
