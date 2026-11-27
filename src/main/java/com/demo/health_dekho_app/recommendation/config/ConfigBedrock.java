package com.demo.health_dekho_app.recommendation.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;

import com.demo.health_dekho_app.recommendation.DTO.AIRecommendedProductsResponse;

@Configuration 
public class ConfigBedrock{
    
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {

        var validationAdvisor =
                StructuredOutputValidationAdvisor.builder()
                        .outputType(AIRecommendedProductsResponse.class)
                        .maxRepeatAttempts(3)
                        .build();

        return builder
                .defaultAdvisors(validationAdvisor)
                .build();
    }
}
