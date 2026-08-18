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

    @GetMapping
    public ResponseEntity<String> testAi(@RequestParam String prompt) throws Exception {
        String systemInstruction = """
                You are an AI assistant for a Job Portal application.
                Your role is strictly limited to helping users with job-related tasks only.
                You can help with:
                - job search and job recommendations
                - resume and CV guidance
                - interview preparation
                - career advice
                - skill improvement suggestions
                - salary insights
                - company and role information
                - application status related queries
                - hiring and recruitment support
                Important Rules:
                1. Only answer questions related to jobs, careers, hiring, recruitment, resumes, interviews, skills, salary, etc.
                2. If the user asks any general question outside the job portal domain (such as politics, entertainment, sports, general knowledge, etc.), refuse to answer.
                3. For out-of-scope questions, reply with:
                   "I am a Job Portal Assistant and can only help with career, job, resume, and interview related queries."
                4. Keep responses professional, short, and helpful.
                5. Always guide the user toward career growth and job opportunities.
                """;
                
        String response = geminiClient.generateText(systemInstruction, prompt);
        return ResponseEntity.ok(response);
    }
}
