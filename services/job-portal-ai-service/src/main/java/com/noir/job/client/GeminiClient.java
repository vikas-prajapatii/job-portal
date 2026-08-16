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

    public GeminiClient(GeminiProperties properties) {
        this.properties = properties;
        this.client = genAiClient(properties);
    }

    public Client genAiClient(GeminiProperties props) {
        return Client.builder().apiKey(props.getKey()).build();
    }

    public String generateText(String prompt) {
        return generateText(prompt, properties.getTemperature(), properties.getMaxOutPutToken());
    }

    public String generateText(String prompt, double temperature, int maxToken) {
        try {
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature((float) temperature)
                    .maxOutputTokens(maxToken)
                    .build();

            String model = properties.getModel() != null ? properties.getModel() : "gemini-2.5-flash";

            GenerateContentResponse response = client.models.generateContent(model, prompt, config);
            return response.text();
        } catch (Exception e) {
            return "Error generating text from Gemini: " + e.getMessage();
        }
    }
}
