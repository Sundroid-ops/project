package com.demo.health_dekho_app.repository;

import com.demo.health_dekho_app.entity.CustomerPolicyEntity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerPolicyRepository extends JpaRepository<CustomerPolicyEntity, Long> {

    @EntityGraph(attributePaths = "product")
    List<CustomerPolicyEntity> findAllByCustomer_IdOrderByCreatedAtDesc(Long customerId);

    boolean existsByCustomer_CustomerIdAndProduct_ProductId(Long customerId, Long productId);
}