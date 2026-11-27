package com.demo.health_dekho_app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistrationDetailsResponse(
    Long productId,
    String productCode,
    String policyName,
    String policyType,
    Integer termMonths,
    String premiumFrequency,
    BigDecimal premiumAmount,
    BigDecimal sumInsured,

    String customerName,
    LocalDate dateOfBirth,
    String phone
) {}