package com.demo.health_dekho_app.recommendation.entity;

import com.demo.health_dekho_app.entity.InsuranceProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "ai_recommendation_item")
public class AIRecommendationItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_item_id")
    private Long recommendationItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "recommendation_request_id",
        nullable = false
    )
    private AIRecommendationRequestEntity recommendationRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private InsuranceProductEntity product;

    @Column(name = "rank_order", nullable = false)
    private Integer rankOrder;

    @Column(name = "recommendation_reason", nullable = false)
    private String recommendationReason;

    @Column(name = "suitability", nullable = false)
    private String suitability;

    @Column(name = "selected_for_registration", nullable = false)
    private Boolean selectedForRegistration = false;

    public Long getRecommendationItemId() {
        return recommendationItemId;
    }

    public void setRecommendationItemId(Long recommendationItemId) {
        this.recommendationItemId = recommendationItemId;
    }

    public AIRecommendationRequestEntity getRecommendationRequest() {
        return recommendationRequest;
    }

    public void setRecommendationRequest(AIRecommendationRequestEntity recommendationRequest) {
        this.recommendationRequest = recommendationRequest;
    }

    public InsuranceProductEntity getProduct() {
        return product;
    }

    public void setProduct(InsuranceProductEntity product) {
        this.product = product;
    }

    public Integer getRankOrder() {
        return rankOrder;
    }

    public void setRankOrder(Integer rankOrder) {
        this.rankOrder = rankOrder;
    }

    public String getRecommendationReason() {
        return recommendationReason;
    }

    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
    }

    public String getSuitability() {
        return suitability;
    }

    public void setSuitability(String suitability) {
        this.suitability = suitability;
    }

    public Boolean getSelectedForRegistration() {
        return selectedForRegistration;
    }

    public void setSelectedForRegistration(Boolean selectedForRegistration) {
        this.selectedForRegistration = selectedForRegistration;
    }

    
}