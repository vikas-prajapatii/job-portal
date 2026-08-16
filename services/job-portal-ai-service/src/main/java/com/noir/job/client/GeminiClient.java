package com.noir.job.client;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.noir.job.config.GeminiProperties;
import org.springframework.stereotype.Component;

@Component
public class GeminiClient {

    private final Client client;
    private final GeminiProperties properties;

    // 1. Fix: Added constructor to initialize the final fields
    public GeminiClient(GeminiProperties properties) {
        this.properties = properties;
        this.client = Client.builder().apiKey(properties.getKey()).build();
    }

    public String generateText(String prompt, double temperature, int maxToken) {
        try {
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature((float) temperature)
                    .maxOutputTokens(maxToken)
                    .build();
                    
            // 2. Fix: Changed genAiClient to client (to match the field definition)
            GenerateContentResponse response = client.models.generateContent(
                    properties.getModel() != null ? properties.getModel() : "gemini-2.5-flash",
                    prompt,
                    config
            );
            return response.text();
        } catch (Exception e) {
            // 3. Fix: Added the '+' operator for string concatenation and wrapped in RuntimeException
            throw new RuntimeException("failed to get responses from gemini: " + e.getMessage(), e);
        }
    }
}
