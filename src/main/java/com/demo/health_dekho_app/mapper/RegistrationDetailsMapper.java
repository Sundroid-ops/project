package com.demo.health_dekho_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.demo.health_dekho_app.DTO.RegistrationDetailsResponse;
import com.demo.health_dekho_app.entity.CustomerEntity;
import com.demo.health_dekho_app.entity.InsuranceProductEntity;

@Mapper (componentModel = "spring")
public interface RegistrationDetailsMapper {

    @Mapping (target = "productId", source = "product.productId")
    @Mapping(target = "productCode", source = "product.productCode")
    @Mapping(target = "policyName", source = "product.policyName")
    @Mapping(target = "policyType", source = "product.policyType")
    @Mapping(target = "termMonths", source = "product.termMonths")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "dateOfBirth", source = "customer.dateOfBirth")
    @Mapping(target = "phone", source = "customer.phone")
    @Mapping(target = "premiumFrequency", source = "product.premiumFrequency")
    @Mapping(target = "premiumAmount", source = "product.premiumAmount")
    @Mapping(target = "sumInsured", source = "product.sumInsured")
    RegistrationDetailsResponse toResponse(CustomerEntity customer, InsuranceProductEntity product);
}
