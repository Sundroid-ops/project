package com.demo.health_dekho_app.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_policy")
public class CustomerPolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    @Column(name = "policy_number", nullable = false, unique = true)
    private String policyNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private InsuranceProductEntity product;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;

    @Column(name = "premium_frequency", nullable = false)
    private String premiumFrequency;

    @Column(name = "premium_amount")
    private BigDecimal premiumAmount;

    @Column(name = "sum_insured")
    private BigDecimal sumInsured;

    @Column(name = "total_premium_payable")
    private BigDecimal totalPremiumPayable;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getPolicyId() {
        return policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public InsuranceProductEntity getProduct() {
        return product;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public String getPremiumFrequency() {
        return premiumFrequency;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public BigDecimal getSumInsured() {
        return sumInsured;
    }

    public String getStatus() {
        return status;
    }
}