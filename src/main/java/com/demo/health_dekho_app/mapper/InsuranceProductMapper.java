package com.demo.health_dekho_app.mapper;

import org.mapstruct.Mapper;

import com.demo.health_dekho_app.DTO.AvailableProductResponse;
import com.demo.health_dekho_app.entity.InsuranceProductEntity;

@Mapper (componentModel = "spring")
public interface InsuranceProductMapper {
    
    AvailableProductResponse toAvailableProductResponse(InsuranceProductEntity insuranceProductEntity);
}
