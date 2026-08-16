package com.noir.job.controller;

import com.noir.job.client.GeminiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiServiceController {

    private final GeminiClient geminiClient;

    // Fix: Use @RequestParam instead of @PathVariable to allow spaces and special characters like "?"
    @GetMapping
    public ResponseEntity<String> testAi(@RequestParam String prompt) {
        String response = geminiClient.generateText(prompt);
        return ResponseEntity.ok(response);
    }
}
