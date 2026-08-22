package com.noir.job.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.noir.job.config.GeminiProperties;
import com.noir.job.exception.AiServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final Client genAiClient;
    private final GeminiProperties props;
    private final ObjectMapper objectMapper;

    public String generateText(String systemInstruction, String prompt) {
        return callText(systemInstruction, prompt,
                (float) props.getTemperature(), props.getMaxOutputTokens());
    }

    public <T> T generateJson(String systemInstruction, String prompt, Class<T> responseType) {
        return callJson(systemInstruction, prompt, responseType);
    }

    // ── private helpers ──────────────────────────────────────────────────────

    private String callText(String systemInstruction, String prompt, float temperature, int maxTokens) {
        try {
            GenerateContentConfig config = buildConfig(systemInstruction,
                    temperature, maxTokens, false);
            GenerateContentResponse response = genAiClient.models
                    .generateContent(props.getModel(), prompt, config);
            String text = response.text();
            log.debug("Gemini response received, length: {}", text.length());
            return text;
        } catch (AiServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage());
            throw new AiServiceException("Failed to get response from Gemini: " + e.getMessage());
        }
    }

    private <T> T callJson(String systemInstruction, String prompt, Class<T> responseType) {
        try {
            GenerateContentConfig config = buildConfig(systemInstruction, 0.3f,
                    props.getMaxOutputTokens(), true);
            GenerateContentResponse response = genAiClient.models.generateContent(
                    props.getModel(), prompt, config);
            return objectMapper.readValue(response.text(), responseType);
        } catch (AiServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to parse Gemini JSON response: {}", e.getMessage());
            throw new AiServiceException("AI returned invalid JSON. Please try again.");
        }
    }

    private GenerateContentConfig buildConfig(String systemInstruction,
                                              float temperature, int maxTokens, boolean jsonMode) {
        GenerateContentConfig.Builder builder = GenerateContentConfig.builder()
                .temperature(temperature)
                .maxOutputTokens(maxTokens);

        if (systemInstruction != null && !systemInstruction.isBlank()) {
            builder.systemInstruction(Content.fromParts(Part.fromText(
                    systemInstruction)));
        }
        if (jsonMode) {
            builder.responseMimeType("application/json");
        }
        return builder.build();
    }
}
