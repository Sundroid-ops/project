package com.demo.health_dekho_app.recommendation.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.health_dekho_app.DTO.AvailableProductResponse;
import com.demo.health_dekho_app.auth.Service.CurrentUserService;
import com.demo.health_dekho_app.entity.CustomerEntity;
import com.demo.health_dekho_app.entity.InsuranceProductEntity;
import com.demo.health_dekho_app.exception.CustomerNotFoundException;
import com.demo.health_dekho_app.recommendation.DTO.AIRecommendationRequest;
import com.demo.health_dekho_app.recommendation.DTO.AIRecommendedProducts;
import com.demo.health_dekho_app.recommendation.DTO.AIRecommendedProductsResponse;
import com.demo.health_dekho_app.recommendation.DTO.RecommendedProducts;
import com.demo.health_dekho_app.recommendation.entity.AIRecommendationItemEntity;
import com.demo.health_dekho_app.recommendation.entity.AIRecommendationRequestEntity;
import com.demo.health_dekho_app.recommendation.prompt.RecommendationPrompt;
import com.demo.health_dekho_app.recommendation.repository.AIRecommendationRequestRepository;
import com.demo.health_dekho_app.recommendation.tools.AvailableProductsTool;
import com.demo.health_dekho_app.repository.CustomerRepository;
import com.demo.health_dekho_app.repository.InsuranceProductRepository;
import com.demo.health_dekho_app.service.InsuranceProductService;


@Service 
public class RecommendationService {
    private final ChatClient chatClient;
    private final InsuranceProductService productService;
    private final AvailableProductsTool availableProductsTool;
    private final RecommendationPrompt recommendationPrompt;
    private final CurrentUserService currentUserService;
    private final CustomerRepository customerRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final AIRecommendationRequestRepository aiRecommendationRequestRepository;

    public RecommendationService(
            ChatClient chatClient,
            InsuranceProductService productService,
            AvailableProductsTool availableProductsTool,
            RecommendationPrompt recommendationPrompt,
            CurrentUserService currentUserService,
            CustomerRepository customerRepository,
            InsuranceProductRepository insuranceProductRepository,
            AIRecommendationRequestRepository aiRecommendationRequestRepository
        ) {
        this.chatClient = chatClient;
        this.productService = productService;
        this.availableProductsTool = availableProductsTool;
        this.recommendationPrompt = recommendationPrompt;
        this.currentUserService = currentUserService;
        this.customerRepository = customerRepository;
        this.insuranceProductRepository = insuranceProductRepository;
        this.aiRecommendationRequestRepository = aiRecommendationRequestRepository;
    }

    @Transactional
    public List<RecommendedProducts> recommend(AIRecommendationRequest request) {

        CustomerEntity customer = customerRepository
                .findById(currentUserService.getCurrentUserId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        LocalDate dob = request.dateOfBirth() != null
                ? request.dateOfBirth()
                : customer.getDateOfBirth();

        if (dob != null && dob.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Date of birth cannot be in the future");
        }

        Integer age = dob != null
                ? Period.between(dob, LocalDate.now()).getYears()
                : null;

        AIRecommendationRequestEntity requestEntity =
                new AIRecommendationRequestEntity();

        requestEntity.setCustomer(customer);
        requestEntity.setInsuranceGoals(request.goalsAndPreferences());
        requestEntity.setPreferredTerm(request.preferredTerm());
        requestEntity.setPreferredPolicyType(request.insuranceType());
        requestEntity.setCalculatedAge(age);

        requestEntity.setModelName("YOUR_MODEL_NAME");
        requestEntity.setModelVersion("YOUR_MODEL_VERSION");
        requestEntity.setPromptVersion("v1");

        List<AvailableProductResponse> initialProducts =
                productService.getAvailableProducts(0, 10);

        if (initialProducts.isEmpty()) {
            aiRecommendationRequestRepository.save(requestEntity);
            return List.of();
        }

        AIRecommendedProductsResponse aiResponse = chatClient.prompt()
                .system(recommendationPrompt.getSystemPrompt())
                .user(recommendationPrompt.getUserPrompt(
                        request, age, initialProducts))
                .tools(availableProductsTool)
                .call()
                .entity(AIRecommendedProductsResponse.class);

        if (aiResponse == null || aiResponse.recommendations() == null) {
            throw new IllegalStateException("Invalid AI response");
        }

        Set<Long> processedIds = new HashSet<>();
        Set<Integer> processedRanks = new HashSet<>();

        for (AIRecommendedProducts aiItem : aiResponse.recommendations()) {

            if (aiItem.productId() == null ||
                    aiItem.rankOrder() == null ||
                    aiItem.rankOrder() < 1 ||
                    aiItem.RecommendationReason() == null ||
                    aiItem.suitability() == null ||
                    !processedIds.add(aiItem.productId()) ||
                    !processedRanks.add(aiItem.rankOrder())) {
                continue;
            }


            InsuranceProductEntity product = insuranceProductRepository
                    .findById(aiItem.productId())
                    .orElse(null);

            if (product == null) {
                continue;
            }


            AIRecommendationItemEntity item =
                    new AIRecommendationItemEntity();

            item.setProduct(product);
            item.setRankOrder(aiItem.rankOrder());
            item.setRecommendationReason(
                    aiItem.RecommendationReason());
            item.setSuitability(aiItem.suitability());


            requestEntity.addItem(item);
        }

        AIRecommendationRequestEntity savedRequest =
                aiRecommendationRequestRepository.save(requestEntity);

        return savedRequest.getItems().stream()
                .sorted(Comparator.comparing(
                        AIRecommendationItemEntity::getRankOrder))
                .map(item -> {

                    InsuranceProductEntity p = item.getProduct();

                    return new RecommendedProducts(
                            p.getProductId(),
                            p.getProductCode(),
                            p.getPolicyName(),
                            p.getPolicyType(),
                            p.getTermMonths(),
                            p.getSumInsured(),
                            p.getPremiumFrequency(),
                            p.getPremiumAmount(),
                            item.getRecommendationReason(),
                            item.getSuitability(),
                            item.getRankOrder()
                    );
                })
                .toList();
    }
}
