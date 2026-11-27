package com.demo.health_dekho_app.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.health_dekho_app.recommendation.entity.AIRecommendationItemEntity;

public interface AIRecommendationItemRepository 
    extends JpaRepository<AIRecommendationItemEntity, Long>{

}
