package com.noir.job.client;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.noir.job.config.GeminiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GeminiClient {
    private final Client client;
    private final GeminiProperties properties;

    public <T> T generateJson(String systemInstruction, String prompt, Class<T> responseClass) {
        try {
            GenerateContentConfig config = buildConfig(systemInstruction, (float) properties.getTemperature(), properties.getMaxOutputTokens(), true);
            String model = properties.getModel() != null ? properties.getModel() : "gemini-3.5-flash";
            GenerateContentResponse response = client.models.generateContent(
                    model,
                    prompt,
                    config
            );
            String jsonText = response.text();
            
            // Strip markdown formatting if present
            if (jsonText.startsWith("```json")) {
                jsonText = jsonText.substring(7);
            } else if (jsonText.startsWith("```")) {
                jsonText = jsonText.substring(3);
            }
            if (jsonText.endsWith("```")) {
                jsonText = jsonText.substring(0, jsonText.length() - 3);
            }
            jsonText = jsonText.trim();
            
            return new com.fasterxml.jackson.databind.ObjectMapper()
                    .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(jsonText, responseClass);
        } catch (Exception e) {
            throw new RuntimeException("failed to parse JSON from gemini: " + e.getMessage(), e);
        }
    }

    public String generateText(String prompt) {
        try {
            return generateText(null, prompt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateText(String systemInstruction, String prompt) throws Exception {
        return generateText(
                systemInstruction,
                prompt,
                properties.getTemperature(),
                properties.getMaxOutputTokens());
    }

    public String generateText(String systemInstruction, String prompt, double temperature, int maxToken) throws Exception {
        return callText(
                systemInstruction, prompt,
                (float) temperature,
                maxToken
        );
    }

    private String callText(String systemInstruction, String prompt,
                            float temperature, int maxToken) throws Exception {
        try {
            GenerateContentConfig config = buildConfig(systemInstruction, temperature, maxToken, false);
            String model = properties.getModel() != null ? properties.getModel() : "gemini-3.5-flash";
            GenerateContentResponse response = client.models.generateContent(
                    model,
                    prompt,
                    config
            );
            return response.text();
        } catch (Exception e) {
            throw new RuntimeException("failed to get responses from gemini: " + e.getMessage(), e);
        }
    }

    private GenerateContentConfig buildConfig(String systemInstruction, float temperature, int maxToken, boolean jsonMode) {
        GenerateContentConfig.Builder builder = GenerateContentConfig.builder()
                .temperature(temperature)
                .maxOutputTokens(maxToken);
                
        if (systemInstruction != null && !systemInstruction.isBlank()) {
            builder.systemInstruction(
                    Content.fromParts(Part.fromText(systemInstruction))
            );
        }
        if (jsonMode) {
            builder.responseMimeType("application/json");
        }
        return builder.build();
    }
}
