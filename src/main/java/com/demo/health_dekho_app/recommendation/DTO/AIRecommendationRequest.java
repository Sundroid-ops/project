package com.demo.health_dekho_app.recommendation.DTO;

import java.time.LocalDate;

public record AIRecommendationRequest(
        LocalDate dateOfBirth,

        String goalsAndPreferences,

        String preferredTerm,

        String insuranceType

) {}