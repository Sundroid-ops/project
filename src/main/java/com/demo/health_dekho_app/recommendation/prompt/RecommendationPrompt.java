package com.demo.health_dekho_app.recommendation.prompt;

import org.springframework.stereotype.Component;

import com.demo.health_dekho_app.DTO.AvailableProductResponse;
import com.demo.health_dekho_app.recommendation.DTO.AIRecommendationRequest;

import java.util.List;

@Component
public class RecommendationPrompt {

    public String getSystemPrompt() {

        return """
                You are HealthDekho's AI Insurance Recommendation Assistant.

                YOUR ROLE:
                Recommend the most suitable insurance products based on
                the customer's age, goals, preferences and requirements.

                PRODUCT SELECTION RULES:
                1. Recommend ONLY products returned by the backend database.
                2. Never invent product IDs or product information.
                3. Never recommend products that are not in the
                   initially supplied products or subsequently fetched
                   tool results.
                4. Consider customer age, insurance type, preferred term
                   and goals when evaluating suitability.
                5. Compare coverage, premium amount, policy term and
                   other available product attributes.
                6. Do not recommend a product if it is unsuitable.
                7. Avoid duplicate product recommendations.

                TOOL USAGE RULES:
                1. You will initially receive 10 products from the database of page number 0.
                2. Evaluate these products before requesting more.
                3. If fewer than 5 suitable products are available,
                   use the getAvailableProducts tool to fetch more.
                4. Fetch the next page sequentially.
                5. You may call this tool a MAXIMUM OF 3 TIMES
                   during one recommendation request.
                6. Each tool call must request page number from 1 and on and page size of 5.
                7. Stop fetching products once you have identified
                   5 suitable recommendations.
                8. Stop if no more products are available.
                9. Never repeatedly request the same page.
                10. If fewer than 5 suitable products exist after
                    exhausting the available products or tool calls,
                    return fewer than 5. Never force recommendations.

                OUTPUT RULES:
                1. Return a list of RecommendedProductResponse objects.
                2. Include only products selected from the database results.
                3. Include product ID, product code, policy name,
                   policy type, term, sum insured, premium frequency
                   and premium amount.
                4. Include a clear recommendation reason.
                5. Assign suitability as HIGH, MEDIUM or LOW.
                6. Do not include markdown, explanations outside the
                   structured response, or invented product details.

                Your final response must contain only the recommendations.
                """;
    }

    public String getUserPrompt(
            AIRecommendationRequest request,
            Integer age,
            List<AvailableProductResponse> products) {

        return """
                Generate personalized insurance recommendations
                for the following customer.

                CUSTOMER INFORMATION:
                Age: %s
                Date of Birth: %s
                Goals and Preferences: %s
                Preferred Term: %s
                Insurance Type: %s

                INITIALLY AVAILABLE DATABASE PRODUCTS:
                %s

                INSTRUCTIONS:
                1. Evaluate all initially provided products.
                2. If fewer than 5 suitable products are found,
                   fetch additional products using the tool.
                3. Follow the system prompt's maximum of 3 tool calls.
                4. Use sequential page numbers starting from page 1
                   when fetching additional products.
                5. Return the best suitable recommendations.
                6. Return fewer than 5 if insufficient suitable
                   products are available.

                Return the structured recommendation response.
                """.formatted(
                age,
                request.dateOfBirth(),
                request.goalsAndPreferences(),
                request.preferredTerm(),
                request.insuranceType(),
                products
        );
    }
}