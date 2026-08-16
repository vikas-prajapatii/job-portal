package com.noir.job.config;

import lombok.Data;

@Data
public class GeminiProperties {
    private String key;
    private String model;
    private int maxOutputTokens = 2048;
    private double temperature = 0.7;
}
