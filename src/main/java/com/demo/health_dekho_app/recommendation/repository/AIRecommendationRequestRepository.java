package com.demo.health_dekho_app.recommendation.repository;

import com.demo.health_dekho_app.recommendation.entity.AIRecommendationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIRecommendationRequestRepository
        extends JpaRepository<AIRecommendationRequestEntity, Long> {
}
