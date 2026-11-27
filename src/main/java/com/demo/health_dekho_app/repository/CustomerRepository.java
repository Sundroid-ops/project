package com.demo.health_dekho_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.health_dekho_app.entity.CustomerEntity;
import com.demo.health_dekho_app.entity.enums.CustomerStatus;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmailIgnoreCase(String email);

    Optional<CustomerEntity> findByIdAndStatus(Long id, CustomerStatus status);

    boolean existsByEmailIgnoreCase(String email);

    Optional<CustomerEntity> findById(Long customerId);
}
