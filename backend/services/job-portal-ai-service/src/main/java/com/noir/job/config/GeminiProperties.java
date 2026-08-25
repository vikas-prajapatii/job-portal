package com.noir.job.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiProperties {

    private String key;
    private String model;
    private int maxOutputTokens = 2048;
    private double temperature = 0.7;
    private String apiKey;

    @PostConstruct
    public void printApiKey() {
        System.out.println("Gemini API Key = " + key);
    }
}
