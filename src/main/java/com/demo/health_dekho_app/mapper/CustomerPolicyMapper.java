package com.demo.health_dekho_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.demo.health_dekho_app.DTO.CustomerPolicyResponse;
import com.demo.health_dekho_app.entity.CustomerPolicyEntity;

@Mapper(componentModel = "spring")
public interface CustomerPolicyMapper {
    
    CustomerPolicyEntity toCustomerPolicyEntity(CustomerPolicyResponse customerPolicyResponse);
    
    @Mapping(target = "policyName", source = "product.policyName")
    @Mapping(target = "policyType", source = "product.policyType")
    @Mapping(target = "termMonths", source = "product.termMonths")
    CustomerPolicyResponse toCustomerPolicyResponse(CustomerPolicyEntity customerPolicyEntity);
}
