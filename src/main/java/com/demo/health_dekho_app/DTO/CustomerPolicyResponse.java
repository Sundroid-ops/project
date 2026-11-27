package com.demo.health_dekho_app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustomerPolicyResponse(
    
    String policyNumber,
    String policyName,
    String policyType,
    Integer termMonths,
    LocalDate startDate,
    LocalDate maturityDate,
    String premiumFrequency,
    BigDecimal premiumAmount,
    BigDecimal sumInsured,
    String status

) {}