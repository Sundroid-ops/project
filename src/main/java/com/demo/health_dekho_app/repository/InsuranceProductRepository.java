package com.demo.health_dekho_app.repository;

import com.demo.health_dekho_app.entity.InsuranceProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InsuranceProductRepository extends JpaRepository<InsuranceProductEntity, Long> {
    @Query("""
        SELECT p
        FROM InsuranceProductEntity p
        WHERE p.active = true
        AND NOT EXISTS (
            SELECT cp
            FROM CustomerPolicyEntity cp
            WHERE cp.product = p
            AND cp.customer.customerId = :customerId
        )"""
    )
    Page<InsuranceProductEntity> findAvailableProducts(@Param("customerId") Long customerId, Pageable page);

    Optional<InsuranceProductEntity> findByProductIdAndActiveTrue(Long productId);
}